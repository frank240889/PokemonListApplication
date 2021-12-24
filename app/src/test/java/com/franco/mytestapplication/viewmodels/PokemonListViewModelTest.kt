package com.franco.mytestapplication.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.franco.mytestapplication.common.State
import com.franco.mytestapplication.data.PokemonRepositoryImpl
import com.franco.mytestapplication.domain.model.local.Pokemon
import com.franco.mytestapplication.presentation.ResourceManagerImpl
import com.franco.mytestapplication.presentation.list.PokemonListViewModel
import com.franco.mytestapplication.utils.EMPTY_STRING
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifySequence
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
 * Unit test for [PokemonListViewModel]
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 24/12/21 at 10:55 a.m.
 */
@ExperimentalCoroutinesApi
class PokemonListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var pokemonListViewModel: PokemonListViewModel

    @MockK
    lateinit var repository: PokemonRepositoryImpl

    @MockK
    lateinit var resourceManager: ResourceManagerImpl

    @RelaxedMockK
    lateinit var pokemonListObserver: Observer<List<Pokemon>>

    @RelaxedMockK
    lateinit var pokemonListLoadingObserver: Observer<Boolean>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        pokemonListViewModel = PokemonListViewModel(repository, resourceManager)
        pokemonListViewModel.run {
            pokemonListObservable.observeForever(pokemonListObserver)
            loadingStateObservable.observeForever(pokemonListLoadingObserver)
        }
    }

    @Test
    fun whenUserFetchesPokemonListThenReturnSuccess() = runBlocking {
        //Arrange

        val mockedList = listOf(
            Pokemon(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING),
            Pokemon(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING),
            Pokemon(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING),
            Pokemon(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING)
        )

        coEvery { repository.fetchPokemons() } returns flow {
            emit(State.Loading)
            emit(State.Success(mockedList))
        }

        //Act

        pokemonListViewModel.fetchPokemons()


        //Assert
        verifySequence {
            pokemonListLoadingObserver.onChanged(true)
            pokemonListObserver.onChanged(mockedList)
            pokemonListLoadingObserver.onChanged(false)
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        pokemonListViewModel.run {
            pokemonListObservable.removeObserver(pokemonListObserver)
            loadingStateObservable.removeObserver(pokemonListLoadingObserver)
        }
    }
}