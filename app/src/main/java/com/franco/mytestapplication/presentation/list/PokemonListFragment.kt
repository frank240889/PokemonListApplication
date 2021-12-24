package com.franco.mytestapplication.presentation.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franco.mytestapplication.R
import com.franco.mytestapplication.databinding.PokemonListFragmentBinding
import com.franco.mytestapplication.common.BaseFragment
import com.franco.mytestapplication.common.PokemonListError
import com.franco.mytestapplication.injection.Injector
import com.franco.mytestapplication.presentation.detail.PokemonDetailFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * Represent the screen that shows the list of pokemons.
 */
class PokemonListFragment : BaseFragment<PokemonListViewModel, PokemonListFragmentBinding>() {

    override val viewModel: PokemonListViewModel
        get() = provideViewModel()

    private val pokemonListAdapter: PokemonListAdapter by lazy {
        PokemonListAdapter { pokemonName ->
            goToDetail(pokemonName)
        }
    }

    companion object {
        fun newInstance() = PokemonListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {

            pokemonListObservable.observe(this@PokemonListFragment) { pokemons ->
                viewBinding.tvErrorMessage.isVisible = false
                pokemonListAdapter.submit(pokemons)
            }

            pokemonListErrorObservable.observe(this@PokemonListFragment, this@PokemonListFragment::handleError)

            loadingStateObservable.observe(this@PokemonListFragment, this@PokemonListFragment::loading)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PokemonListFragmentBinding.inflate(layoutInflater).apply {
        _viewBinding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setList()
        viewModel.fetchPokemons()
    }

    private fun setList() {
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = pokemonListAdapter
        }
    }

    override fun loading(loading: Boolean) {
        viewBinding.pbLoading.isVisible = loading
    }

    override fun provideViewModel() = ViewModelProvider(
        this,
        viewModelFactory
    )[PokemonListViewModel::class.java]

    private fun handleError(errorData: PokemonListError) {
        viewBinding.tvErrorMessage.isVisible = true

        Snackbar.make(viewBinding.root, errorData.errorMessage, Snackbar.LENGTH_INDEFINITE).apply {
            if (errorData.action == PokemonListViewModel.Action.RETRY) {
                setAction(R.string.retry) {
                    viewModel.fetchPokemons()
                }
            }

            //Don't allow dismiss the snackbar
            behavior = object : BaseTransientBottomBar.Behavior() {
                override fun canSwipeDismissView(child: View) = false
            }
        }.show()
    }

    private fun goToDetail(pokemonName: String) {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right
            )
            .replace(R.id.fl_fragment_container, PokemonDetailFragment.newInstance(pokemonName))
            .addToBackStack(PokemonDetailFragment::class.java.name)
            .commit()
    }

}