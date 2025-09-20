package com.rbs.pokecompose.data.mapper

import com.rbs.pokecompose.data.local.entity.DetailPokemonEntity
import com.rbs.pokecompose.data.local.entity.PokemonEntity
import com.rbs.pokecompose.data.remote.DetailPokemonResponse
//import com.rbs.pokecompose.data.remote.DetailPokemonResponse
import com.rbs.pokecompose.data.remote.ItemPokemon
import com.rbs.pokecompose.domain.model.DetailPokemon
import com.rbs.pokecompose.domain.model.Pokemon

fun PokemonEntity.toDomain() = Pokemon(
    id = id,
    name = name
)

fun ItemPokemon.toEntity(): PokemonEntity {
    val id = url.split("/").dropLast(1).last().toInt()
    return PokemonEntity(
        id = id,
        name = name
    )
}

fun DetailPokemonResponse.toDomain() = DetailPokemon(
    id = id,
    name = name,
    abilities = abilities.map { it.ability.name },
    image = sprites?.other?.artwork?.image.orEmpty()
)

fun DetailPokemonResponse.toEntity() = DetailPokemonEntity(
    id = id,
    name = name,
    abilities = abilities.map { it.ability.name },
    image = sprites?.other?.artwork?.image.orEmpty()
)

fun DetailPokemonEntity.toDomain() = DetailPokemon(
    id = id,
    name = name,
    abilities = abilities,
    image = image
)