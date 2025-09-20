package com.rbs.pokecompose.domain.usecase

import androidx.paging.PagingData
import com.rbs.pokecompose.domain.model.Pokemon
import com.rbs.pokecompose.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class SearchPokemonUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Pokemon>> = repository.searchPokemon(query)
}