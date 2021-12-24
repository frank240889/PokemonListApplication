package com.franco.mytestapplication.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franco.mytestapplication.R
import com.franco.mytestapplication.common.BaseViewModel
import com.franco.mytestapplication.common.PokemonListError
import com.franco.mytestapplication.common.ResourceManager
import com.franco.mytestapplication.common.State
import com.franco.mytestapplication.data.PokemonRepository
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.utils.orDefault
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokemonListViewModel constructor(
    private val repository: PokemonRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {
    private val _pokemonListObservable: MutableLiveData<List<Pokemon>> by lazy {
        MutableLiveData()
    }

    val pokemonListObservable: LiveData<List<Pokemon>> get() = _pokemonListObservable

    private val _pokemonListErrorObservable: MutableLiveData<PokemonListError> by lazy {
        MutableLiveData()
    }

    val pokemonListErrorObservable: LiveData<PokemonListError> get() = _pokemonListErrorObservable

    fun fetchPokemons() = viewModelScope.launch {
        repository.fetchPokemons().collect {
            when(it) {
                is State.Success -> {
                    _pokemonListObservable.value = it.data
                    setLoading(false)
                }
                is State.Error -> {
                    handleError(it)
                    setLoading(false)
                }
                is State.Loading -> setLoading(true)
            }
        }
    }

    private fun handleError(it: State.Error) {
        _pokemonListErrorObservable.value =
            PokemonListError(
                Action.RETRY,
                it.error.message.orDefault(resourceManager.getString(R.string.generic_error_message))
            )
    }


    enum class Action {
        RETRY,
        IGNORE
    }
}