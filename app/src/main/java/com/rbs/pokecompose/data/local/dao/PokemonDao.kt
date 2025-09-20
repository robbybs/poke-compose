package com.rbs.pokecompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbs.pokecompose.data.local.entity.DetailPokemonEntity
import com.rbs.pokecompose.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemons(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun getPokemons(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemons")
    suspend fun clearAllPokemons()

    @Query("SELECT * FROM pokemons WHERE name LIKE :query")
    fun searchPokemons(query: String): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(pokemon: DetailPokemonEntity)

    @Query("SELECT * FROM pokemon_detail WHERE id = :id")
    suspend fun getDetailById(id: Int): DetailPokemonEntity?
}