package com.rbs.pokecompose.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rbs.pokecompose.data.local.PokeDatabase
import com.rbs.pokecompose.data.local.dao.PokemonDao
import com.rbs.pokecompose.data.local.dao.PokemonRemoteKeysDao
import com.rbs.pokecompose.data.mapper.toDomain
import com.rbs.pokecompose.data.mapper.toEntity
import com.rbs.pokecompose.data.mediator.PokemonRemoteMediator
import com.rbs.pokecompose.data.remote.PokemonApiService
import com.rbs.pokecompose.domain.model.DetailPokemon
import com.rbs.pokecompose.domain.model.Pokemon
import com.rbs.pokecompose.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepoImpl(
    private val apiService: PokemonApiService,
    private val database: PokeDatabase,
    private val pokemonDao: PokemonDao,
    private val remoteKeysDao: PokemonRemoteKeysDao,
) : PokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = PokemonRemoteMediator(apiService, database, pokemonDao, remoteKeysDao),
        pagingSourceFactory = { pokemonDao.getPokemons() }
    ).flow.map { pagingData ->
        pagingData.map { entity -> entity.toDomain() }
    }

    override fun searchPokemon(query: String): Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pokemonDao.searchPokemons("%$query%") }
    ).flow.map { pagingData ->
        pagingData.map { entity -> entity.toDomain() }
    }

    override suspend fun getDetailPokemon(id: Int): DetailPokemon =
        withContext(Dispatchers.IO) {
            val cachedData = pokemonDao.getDetailById(id)
            if (cachedData != null) {
                return@withContext cachedData.toDomain()
            }

            val result = apiService.getDetailPokemon(id)
            pokemonDao.insertDetail(result.toEntity())
            result.toDomain()
        }
}