package com.kfaraj.samples.pokedex.data

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.InitializeAction
import androidx.paging.RemoteMediator.MediatorResult
import com.kfaraj.samples.pokedex.data.local.PokemonEntity
import com.kfaraj.samples.pokedex.data.local.PokemonsLocalDataSource
import com.kfaraj.samples.pokedex.data.remote.NamedApiResource
import com.kfaraj.samples.pokedex.data.remote.NamedApiResourceList
import com.kfaraj.samples.pokedex.data.remote.PokemonsRemoteDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PokemonsRemoteMediatorTest {

}
