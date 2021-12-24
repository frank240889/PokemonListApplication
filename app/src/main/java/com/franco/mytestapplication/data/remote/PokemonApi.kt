package com.franco.mytestapplication.data.remote

import com.franco.mytestapplication.domain.model.remote.detail.PokemonDetailResponse
import com.franco.mytestapplication.domain.model.remote.detail.PokemonDetailResponse2
import com.franco.mytestapplication.domain.model.remote.list.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The pokemon API.
 */
interface PokemonApi {

    @GET("pokemon")
    suspend fun fetchPokemons(): PokemonsResponse

    @GET("pokemon/{pokemon}")
    suspend fun pokemon(@Path("pokemon") pokemon: String): PokemonDetailResponse2

}