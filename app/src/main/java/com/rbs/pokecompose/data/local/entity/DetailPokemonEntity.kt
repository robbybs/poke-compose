package com.rbs.pokecompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_detail")
data class DetailPokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val abilities: List<String>,
    val image: String
)