package com.franco.mytestapplication.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franco.mytestapplication.R
import com.franco.mytestapplication.common.BaseViewModel
import com.franco.mytestapplication.common.ResourceManager
import com.franco.mytestapplication.data.PokemonRepository
import com.franco.mytestapplication.domain.model.local.Pokemon

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    private val _pokemonObservable: MutableLiveData<Pokemon> by lazy {
        MutableLiveData()
    }

    val pokemonObservable: LiveData<Pokemon> get() = _pokemonObservable

    private val _pokemonErrorObservable: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val pokemonErrorObservable: LiveData<String> get() = _pokemonErrorObservable

    fun fetchPokemon(pokemonName: String) {
        setLoading(true)
        repository.fetchPokemon(pokemonName)?.let { pokemon ->
            _pokemonObservable.value = pokemon
            setLoading(false)
        } ?: launchError()
    }

    private fun launchError() {
        _pokemonErrorObservable.value = resourceManager.getString(R.string.pokemon_details_not_found)
        setLoading(false)
    }

}