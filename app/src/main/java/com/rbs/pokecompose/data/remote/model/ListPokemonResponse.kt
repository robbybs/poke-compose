package com.rbs.pokecompose.data.remote.model

data class ListPokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ItemPokemon>
)

data class ItemPokemon(
    val name: String,
    val url: String
)