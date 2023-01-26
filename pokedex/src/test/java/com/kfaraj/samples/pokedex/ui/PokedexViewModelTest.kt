package com.kfaraj.samples.pokedex.ui

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kfaraj.samples.pokedex.data.Pokemon
import com.kfaraj.samples.pokedex.data.PokemonsRepository
import com.kfaraj.samples.pokedex.domain.GetSpriteUseCase
import com.kfaraj.samples.pokedex.testutils.MainDispatcherRule
import com.kfaraj.samples.pokedex.ui.pokedex.PokedexViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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

        fakePokemonRepo = mock<PokemonsRepository>().apply {
            whenever(getPagingDataStream(PagingConfig(1))).thenReturn(fakePagingData)
        }

        fakeSpritUseCase = mock()

        pokedexViewModel = PokedexViewModel(fakePokemonRepo, fakeSpritUseCase)
    }

    @Test
    fun pagingDataTest() = runTest {
        val result = pokedexViewModel.pagingData.first()

        assert(
            result == fakePagingData.first()
        )
    }

    companion object {
        private val PIKACHU = Pokemon(25, "Pikachu")
        private val MEW = Pokemon(151, "Mew")
    }

}
