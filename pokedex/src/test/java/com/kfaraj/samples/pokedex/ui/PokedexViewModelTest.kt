package com.kfaraj.samples.pokedex.ui

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.kfaraj.samples.pokedex.data.Pokemon
import com.kfaraj.samples.pokedex.data.PokemonsRepository
import com.kfaraj.samples.pokedex.domain.GetSpriteUseCase
import com.kfaraj.samples.pokedex.testutils.MainDispatcherRule
import com.kfaraj.samples.pokedex.testutils.TestItemCallback
import com.kfaraj.samples.pokedex.testutils.TestListUpdateCallback
import com.kfaraj.samples.pokedex.ui.pokedex.PokedexItemUiState
import com.kfaraj.samples.pokedex.ui.pokedex.PokedexViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PokedexViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakePagingData : Flow<PagingData<Pokemon>>
    private lateinit var fakePokemonRepo : PokemonsRepository
    private lateinit var fakeSpritUseCase : GetSpriteUseCase
    private lateinit var pokedexViewModel : PokedexViewModel

    @Before
    fun initTest() {
        fakePagingData = flowOf(PagingData.from(listOf(PIKACHU, MEW)))
        fakeSpritUseCase = mock<GetSpriteUseCase>().apply {
            whenever(invoke(any())).thenReturn(SPRITE_USECASE)
        }

        fakePokemonRepo = mock<PokemonsRepository>().apply {
            whenever(getPagingDataStream(any())).thenReturn(fakePagingData)
        }

        pokedexViewModel = PokedexViewModel(fakePokemonRepo, fakeSpritUseCase)
    }

    @Test
    fun pagingDataTest() = runTest {
        val result = pokedexViewModel.pagingData.first()
        val differ = AsyncPagingDataDiffer(
            diffCallback = TestItemCallback<PokedexItemUiState>(),
            updateCallback = TestListUpdateCallback(),
            workerDispatcher = mainDispatcherRule.testDispatcher
        )
        differ.submitData(result)
        advanceUntilIdle()

        assertEquals(
            listOf(PIKACHU_UI_STATE, MEW_UI_STATE),
            differ.snapshot().items
        )
    }

    companion object {
        private const val SPRITE_USECASE = "spriteUseCase"

        private val PIKACHU = Pokemon(25, "Pikachu")
        private val MEW = Pokemon(151, "Mew")


        private val PIKACHU_UI_STATE = PokedexItemUiState(25, "Pikachu", SPRITE_USECASE)
        private val MEW_UI_STATE = PokedexItemUiState(151, "Mew", SPRITE_USECASE)
    }
}
