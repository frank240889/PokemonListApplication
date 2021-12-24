package com.franco.mytestapplication.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.franco.mytestapplication.R
import com.franco.mytestapplication.common.State
import com.franco.mytestapplication.data.PokemonRepositoryImpl
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.presentation.ResourceManagerImpl
import com.franco.mytestapplication.presentation.detail.PokemonDetailViewModel
import com.franco.mytestapplication.presentation.list.PokemonListViewModel
import com.franco.mytestapplication.utils.EMPTY_STRING
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifySequence
import java.lang.Exception
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for [PokemonDetailViewModel]
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 24/12/21 at 10:55 a.m.
 */
@ExperimentalCoroutinesApi
class PokemonDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    @MockK
    lateinit var repository: PokemonRepositoryImpl

    @MockK
    lateinit var resourceManager: ResourceManagerImpl

    @RelaxedMockK
    lateinit var pokemonDetailObserver: Observer<Pokemon>

    @RelaxedMockK
    lateinit var pokemonErrorDetailObserver: Observer<String>

    @RelaxedMockK
    lateinit var pokemonDetailLoadingObserver: Observer<Boolean>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        pokemonDetailViewModel = PokemonDetailViewModel(repository, resourceManager)
        pokemonDetailViewModel.run {
            pokemonObservable.observeForever(pokemonDetailObserver)
            loadingStateObservable.observeForever(pokemonDetailLoadingObserver)
            pokemonErrorObservable.observeForever(pokemonErrorDetailObserver)
        }
    }

    @Test
    fun whenUserFetchesPokemonListThenReturnSuccess() {

        //Arrange
        val mockedPokemon = Pokemon(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        every { repository.fetchPokemon(EMPTY_STRING) } returns mockedPokemon

        //Act

        pokemonDetailViewModel.fetchPokemon(EMPTY_STRING)

        //Assert
        verifySequence {
            pokemonDetailLoadingObserver.onChanged(true)
            pokemonDetailObserver.onChanged(mockedPokemon)
            pokemonDetailLoadingObserver.onChanged(false)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        pokemonDetailViewModel.run {
            pokemonObservable.removeObserver(pokemonDetailObserver)
            loadingStateObservable.removeObserver(pokemonDetailLoadingObserver)
            pokemonErrorObservable.removeObserver(pokemonErrorDetailObserver)
        }
    }
}