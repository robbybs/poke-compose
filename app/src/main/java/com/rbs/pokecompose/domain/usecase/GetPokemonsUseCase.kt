package com.rbs.pokecompose.domain.usecase

import androidx.paging.PagingData
import com.rbs.pokecompose.domain.model.Pokemon
import com.rbs.pokecompose.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonsUseCase(private val repository: PokemonRepository) {
    operator fun invoke(): Flow<PagingData<Pokemon>> = repository.getPokemons()
}