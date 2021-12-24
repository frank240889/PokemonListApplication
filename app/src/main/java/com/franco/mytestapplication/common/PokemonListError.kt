package com.franco.mytestapplication.common

import com.franco.mytestapplication.presentation.list.PokemonListViewModel

/**
 * Represent a error state data and the action to do.
 */
data class PokemonListError(
    val action: PokemonListViewModel.Action,
    val errorMessage: String
)
