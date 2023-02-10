package com.kfaraj.samples.pokedex.ui

import androidx.lifecycle.SavedStateHandle
import com.kfaraj.samples.pokedex.data.Pokemon
import com.kfaraj.samples.pokedex.data.PokemonsRepository
import com.kfaraj.samples.pokedex.domain.GetSpriteUseCase
import com.kfaraj.samples.pokedex.testutils.MainDispatcherRule
import com.kfaraj.samples.pokedex.ui.pokemon.PokemonUiState
import com.kfaraj.samples.pokedex.ui.pokemon.PokemonViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PokemonViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeSavedStateHandle: SavedStateHandle
    private lateinit var fakeGetSpriteUseCase: GetSpriteUseCase
    private lateinit var fakePokemonsRepository: PokemonsRepository
    private lateinit var fakeViewModel: PokemonViewModel

    @Test
    fun uiStateTest() = runTest {
        fakeSavedStateHandle = SavedStateHandle(mapOf(PARAM_NAME to 0))

        fakeGetSpriteUseCase = mock<GetSpriteUseCase>().apply {
            whenever(invoke(any())).thenReturn(SPRITE)
        }

        fakePokemonsRepository = mock<PokemonsRepository>().apply {
            whenever(get(0)).thenReturn(PIKACHU)
        }

        fakeViewModel = PokemonViewModel(
            fakeSavedStateHandle,
            fakePokemonsRepository,
            fakeGetSpriteUseCase
        )

        assertEquals(PIKA_UI_STATE, fakeViewModel.uiState.value)
    }

    companion object {
        private const val PARAM_NAME = "id"
        private const val SPRITE = "spriteUseCase"
        private val PIKACHU = Pokemon(25, "Pikachu")
        private val PIKA_UI_STATE = PokemonUiState(PIKACHU.id, PIKACHU.name, SPRITE)
    }

}
