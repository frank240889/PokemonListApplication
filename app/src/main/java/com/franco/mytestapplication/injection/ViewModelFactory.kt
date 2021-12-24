package com.franco.mytestapplication.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.franco.mytestapplication.common.ResourceManager
import com.franco.mytestapplication.data.PokemonRepository
import com.franco.mytestapplication.presentation.detail.PokemonDetailViewModel
import com.franco.mytestapplication.presentation.list.PokemonListViewModel
import javax.inject.Inject

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 23/12/21 at 11:28 p.m.
 */
class ViewModelFactory @Inject constructor(
    private val repository: PokemonRepository,
    private val resourceManager: ResourceManager
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            PokemonListViewModel::class.java -> {
                PokemonListViewModel(repository, resourceManager) as T
            }
            PokemonDetailViewModel::class.java -> {
                PokemonDetailViewModel(repository, resourceManager) as T
            }
            else -> {
                error("ViewModel not found.")
            }
        }
    }
}