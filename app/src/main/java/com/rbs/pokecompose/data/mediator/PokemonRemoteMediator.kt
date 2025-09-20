package com.rbs.pokecompose.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rbs.pokecompose.data.local.PokeDatabase
import com.rbs.pokecompose.data.local.dao.PokemonDao
import com.rbs.pokecompose.data.local.dao.PokemonRemoteKeysDao
import com.rbs.pokecompose.data.local.entity.PokemonEntity
import com.rbs.pokecompose.data.local.entity.PokemonRemoteKeysEntity
import com.rbs.pokecompose.data.mapper.toEntity
import com.rbs.pokecompose.data.remote.PokemonApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val apiService: PokemonApiService,
    private val database: PokeDatabase,
    private val pokemonDao: PokemonDao,
    private val remoteKeysDao: PokemonRemoteKeysDao
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 0
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val response = apiService.getPokemons(
                limit = state.config.pageSize,
                offset = page * state.config.pageSize
            )

            val pokemons = response.results.map { pokemonResult -> pokemonResult.toEntity() }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDao.clearAllPokemons()
                    remoteKeysDao.clearRemoteKeys()
                }

                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (response.results.isEmpty()) null else page + 1
                val keys = pokemons.map {
                    PokemonRemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeysDao.insertAll(keys)
                pokemonDao.insertAllPokemons(pokemons)
            }

            MediatorResult.Success(endOfPaginationReached = response.results.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonEntity>): PokemonRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                remoteKeysDao.getRemoteKeysById(pokemon.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonEntity>): PokemonRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                remoteKeysDao.getRemoteKeysById(pokemon.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonEntity>): PokemonRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeysById(id)
            }
        }
    }
}