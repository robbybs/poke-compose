package com.rbs.pokecompose.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ListPokemonResponse

    @GET("pokemon/{id}")
    suspend fun getDetailPokemon(@Path("id") id: Int): DetailPokemonResponse
}