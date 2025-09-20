package com.rbs.pokecompose.domain.usecase

import com.rbs.pokecompose.domain.model.DetailPokemon
import com.rbs.pokecompose.domain.repository.PokemonRepository

class GetDetailPokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int): DetailPokemon = repository.getDetailPokemon(id)
}