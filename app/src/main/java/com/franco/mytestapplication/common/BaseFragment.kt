package com.franco.mytestapplication.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.franco.mytestapplication.databinding.PokemonListFragmentBinding
import com.franco.mytestapplication.injection.Injector
import com.franco.mytestapplication.injection.ViewModelFactory

/**
 * Provides common functionality for other fragments.
 */
abstract class BaseFragment<V, B>: Fragment() {

    internal lateinit var viewModelFactory: ViewModelFactory

    abstract val viewModel: V

    internal var _viewBinding: B? = null
    internal val viewBinding: B get() = _viewBinding!!

    internal abstract fun loading(loading: Boolean)

    internal abstract fun provideViewModel(): V

    override fun onAttach(context: Context) {
        viewModelFactory = Injector.injectViewModelFactory(context)
        super.onAttach(context)
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


}