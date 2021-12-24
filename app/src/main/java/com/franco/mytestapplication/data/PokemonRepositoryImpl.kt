package com.franco.mytestapplication.data

import com.franco.mytestapplication.R
import com.franco.mytestapplication.common.ResourceManager
import com.franco.mytestapplication.common.State
import com.franco.mytestapplication.data.remote.PokemonApi
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.utils.toSingleTextLineAbility
import com.franco.mytestapplication.utils.toSingleTextLineType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Repository to access API data.
 */
class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val resourceManager: ResourceManager
): PokemonRepository {

    // Memory cache to avoid make new call request in screen orientation change.
    private val inMemoryAppCache: MutableList<Pokemon> by lazy {
        ArrayList()
    }

    override fun fetchPokemons(): Flow<State<List<Pokemon>>> = flow {
        emit(State.Loading)
        if (inMemoryAppCache.isEmpty()) {
            pokemonApi.run {
                val pokemons = fetchPokemons()
                pokemons
                    .results?.map {
                        val pokemon = pokemon(it.name.orEmpty())
                        Pokemon(
                            it.name,
                            pokemon.sprites?.frontDefault.orEmpty(),
                            pokemon.abilities?.toSingleTextLineAbility().orEmpty(),
                            pokemon.types?.toSingleTextLineType().orEmpty()
                        )
                    }
                    .orEmpty()
                    .let { list ->
                        inMemoryAppCache.apply {
                            clear()
                            addAll(list)
                        }
                    }
            }
        }
        emit(State.Success(inMemoryAppCache))
    }
        .catch {
            emit(State.Error(IllegalStateException(resourceManager.getString(R.string.generic_error_message))))
        }

    override fun fetchPokemon(pokemonName: String) = inMemoryAppCache.find { pokemon -> pokemon.name == pokemonName }

}