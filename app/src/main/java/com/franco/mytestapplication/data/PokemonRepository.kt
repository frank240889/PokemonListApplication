package com.franco.mytestapplication.data

import com.franco.mytestapplication.common.State
import com.franco.mytestapplication.data.remote.PokemonApi
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.domain.model.remote.detail.PokemonDetailResponse2
import kotlinx.coroutines.flow.Flow

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 23/12/21 at 6:33 p.m.
 */
interface PokemonRepository {
    fun fetchPokemons(): Flow<State<List<Pokemon>>>

    fun fetchPokemon(pokemonName: String): Pokemon?
}