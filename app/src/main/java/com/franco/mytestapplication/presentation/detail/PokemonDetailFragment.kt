package com.franco.mytestapplication.presentation.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.franco.mytestapplication.R
import com.franco.mytestapplication.common.BaseFragment
import com.franco.mytestapplication.databinding.PokemonDetailFragmentBinding
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.utils.loadUrlImage
import com.franco.mytestapplication.utils.orDefault

class PokemonDetailFragment() : BaseFragment<PokemonDetailViewModel, PokemonDetailFragmentBinding>() {

    private val pokemonName: String by lazy {
        requireArguments().getString(POKEMON_NAME).orDefault()
    }
    companion object {
        const val POKEMON_NAME = "pokemon_name"

        fun newInstance(pokemonName: String) = PokemonDetailFragment().apply {
            arguments = Bundle().apply {
                putString(POKEMON_NAME, pokemonName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            pokemonObservable.observe(
                this@PokemonDetailFragment,
                this@PokemonDetailFragment::handlePokemon
            )

            pokemonErrorObservable.observe(
                this@PokemonDetailFragment,
                this@PokemonDetailFragment::handleError
            )

            loadingStateObservable.observe(
                this@PokemonDetailFragment,
                this@PokemonDetailFragment::loading
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PokemonDetailFragmentBinding.inflate(layoutInflater).apply {
        _viewBinding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.fetchPokemon(pokemonName)
    }

    override fun loading(loading: Boolean) {

    }

    override fun provideViewModel() = ViewModelProvider(
        this,
        viewModelFactory
    )[PokemonDetailViewModel::class.java]

    override val viewModel: PokemonDetailViewModel
        get() = provideViewModel()

    private fun handlePokemon(pokemon: Pokemon) {
        viewBinding.run {
            tvDetailPokemonName.text = pokemon.name
            ivPokemonPreview.loadUrlImage(pokemon.preview)
            tietDetailPokemonAbilities.setText(pokemon.abilities.orEmpty())
            tietDetailPokemonType.setText(pokemon.type)
        }
    }

    private fun handleError(error: String) {

    }

}