package com.kfaraj.samples.pokedex.ui

import androidx.lifecycle.SavedStateHandle
import com.kfaraj.samples.pokedex.data.Pokemon
import com.kfaraj.samples.pokedex.data.PokemonsRepository
import com.kfaraj.samples.pokedex.domain.GetSpriteUseCase
import com.kfaraj.samples.pokedex.testutils.MainDispatcherRule
import com.kfaraj.samples.pokedex.ui.pokedex.PokedexItemUiState
import com.kfaraj.samples.pokedex.ui.pokemon.PokemonUiState
import com.kfaraj.samples.pokedex.ui.pokemon.PokemonViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PokemonViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeSavedStateHandle: SavedStateHandle
    private lateinit var fakeGetSpriteUseCase: GetSpriteUseCase
    private lateinit var fakePokemonsRepository: PokemonsRepository
    private lateinit var fakePokemonUiState: PokemonUiState
    private lateinit var fakeViewModel: PokemonViewModel

    @Before
    suspend fun init() {
        fakeSavedStateHandle = mock()
        fakeGetSpriteUseCase = mock()

        fakePokemonUiState = PokemonUiState(
            PIKACHU.id,
            PIKACHU.name,
            fakeGetSpriteUseCase.invoke(PIKACHU.id)
        )

        fakePokemonsRepository = mock<PokemonsRepository>().apply {
            whenever(get(1)).thenReturn(PIKACHU)
        }

        fakeViewModel = PokemonViewModel(
            fakeSavedStateHandle,
            fakePokemonsRepository,
            fakeGetSpriteUseCase
        )
    }

    @Test
    fun uiStateTest() = runTest {
        assert(fakeViewModel.uiState.value == fakePokemonUiState)
    }

    companion object {
        private val PIKACHU = Pokemon(25, "Pikachu")
        private val MEW = Pokemon(151, "Mew")
    }

}
