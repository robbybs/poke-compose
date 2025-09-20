package com.rbs.pokecompose.domain.model

data class DetailPokemon(
    val id: Int,
    val name: String,
    val abilities: List<String>,
    val image: String
)
