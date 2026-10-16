package com.example.amphibians

import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphibian
import com.example.amphibians.ui.screens.AmphibiansUiState
import com.example.amphibians.ui.screens.AmphibiansViewModel
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import kotlinx.serialization.SerializationException
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class AmphibiansViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    @Before fun setUp() { Dispatchers.setMain(dispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }

    @Test fun networkFailureCanRetryToSuccess() = runTest(dispatcher) {
        var fail = true
        val frog = Amphibian("Frog", "Frog", "Green frog", "https://example.test/frog.png")
        val model = AmphibiansViewModel(object : AmphibiansRepository {
            override suspend fun getAmphibians(): List<Amphibian> {
                if (fail) throw IOException("Offline")
                return listOf(frog)
            }
        })
        assertEquals(AmphibiansUiState.Loading, model.amphibiansUiState)
        advanceUntilIdle()
        assertEquals(AmphibiansUiState.Error, model.amphibiansUiState)
        fail = false
        model.getAmphibians()
        advanceUntilIdle()
        assertEquals(AmphibiansUiState.Success(listOf(frog)), model.amphibiansUiState)
    }

    @Test fun malformedResponseProducesErrorInsteadOfCrashing() = runTest(dispatcher) {
        val model = AmphibiansViewModel(object : AmphibiansRepository {
            override suspend fun getAmphibians(): List<Amphibian> = throw SerializationException("Bad JSON")
        })
        advanceUntilIdle()
        assertEquals(AmphibiansUiState.Error, model.amphibiansUiState)
    }
}
