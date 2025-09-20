package com.rbs.pokecompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rbs.pokecompose.data.local.dao.PokemonDao
import com.rbs.pokecompose.data.local.dao.PokemonRemoteKeysDao
import com.rbs.pokecompose.data.local.dao.UserDao
import com.rbs.pokecompose.data.local.entity.DetailPokemonEntity
import com.rbs.pokecompose.data.local.entity.PokemonEntity
import com.rbs.pokecompose.data.local.entity.PokemonRemoteKeysEntity
import com.rbs.pokecompose.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, PokemonEntity::class, PokemonRemoteKeysEntity::class, DetailPokemonEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PokeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonRemoteKeysDao(): PokemonRemoteKeysDao
}