package com.rbs.pokecompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.rbs.pokecompose.data.local.PokeDatabase
import com.rbs.pokecompose.data.mapper.toDomain
import com.rbs.pokecompose.data.mapper.toEntity
import com.rbs.pokecompose.data.mediator.PokemonRemoteMediator
import com.rbs.pokecompose.data.remote.apiservice.PokemonApiService
import com.rbs.pokecompose.domain.model.DetailPokemon
import com.rbs.pokecompose.domain.model.Pokemon
import com.rbs.pokecompose.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonRepoImpl(
    private val apiService: PokemonApiService,
    private val database: PokeDatabase
) : PokemonRepository {
    private val pokemonDao = database.pokemonDao()
    private val remoteKeysDao = database.pokemonRemoteKeysDao()

    companion object {
        private const val PAGE_SIZE = 10
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemons(): Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(PAGE_SIZE),
        remoteMediator = PokemonRemoteMediator(apiService, database, pokemonDao, remoteKeysDao),
        pagingSourceFactory = { pokemonDao.getPokemons() }
    ).flow.map { pagingData ->
        pagingData.map { entity -> entity.toDomain() }
    }

    override fun searchPokemon(query: String): Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(PAGE_SIZE),
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