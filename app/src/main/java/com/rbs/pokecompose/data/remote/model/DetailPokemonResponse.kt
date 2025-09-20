package com.rbs.pokecompose.data.remote.model

import com.google.gson.annotations.SerializedName

data class DetailPokemonResponse(
    val id: Int,
    val name: String,
    val abilities: List<Abilities>,
    val sprites: Sprites?
)

data class Abilities(
    val ability: Ability
)

data class Ability(
    val name: String
)

data class Sprites(
    val other: Other?
)

data class Other(
    @SerializedName("official-artwork")
    val artwork: OfficialArtwork?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val image: String
)