package com.example.coroutines

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.currentTime
import org.junit.Assert.*
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class WeatherTest {
    @Test fun sequentialTakesTwoDelays() = runTest {
        assertEquals("Sunny 30°C", sequentialWeather())
        assertEquals(2_000L, currentTime)
    }
    @Test fun concurrentOverlapsDelays() = runTest {
        assertEquals("Sunny 30°C", concurrentWeather())
        assertEquals(1_000L, currentTime)
    }
    @Test fun cancellationStopsPendingWork() = runTest {
        assertTrue(cancellationExample())
        assertEquals(100L, currentTime)
    }
    @Test fun expectedFailureReturnsFallback() = runTest {
        assertEquals("Sunny --°C", exceptionExample())
    }
}
