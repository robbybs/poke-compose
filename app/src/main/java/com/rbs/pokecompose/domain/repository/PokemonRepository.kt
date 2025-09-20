package com.rbs.pokecompose.domain.repository

import androidx.paging.PagingData
import com.rbs.pokecompose.domain.model.DetailPokemon
import com.rbs.pokecompose.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<PagingData<Pokemon>>
    fun searchPokemon(query: String): Flow<PagingData<Pokemon>>
    suspend fun getDetailPokemon(id: Int): DetailPokemon
}