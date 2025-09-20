package com.rbs.pokecompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbs.pokecompose.data.local.entity.PokemonRemoteKeysEntity

@Dao
interface PokemonRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<PokemonRemoteKeysEntity>)

    @Query("SELECT * FROM pokemon_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysById(id: Int): PokemonRemoteKeysEntity?

    @Query("DELETE FROM pokemon_remote_keys")
    suspend fun clearRemoteKeys()
}