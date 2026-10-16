package com.example.marsphotos

import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.fake.FakeDataSource
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.rules.TestDispatcherRule
import com.example.marsphotos.ui.screens.MarsUiState
import com.example.marsphotos.ui.screens.MarsViewModel
import java.io.IOException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MarsErrorTest {
    @get:Rule val dispatcher = TestDispatcherRule()

    @Test fun failedRequestCanRetry() = runTest {
        var fail = true
        val model = MarsViewModel(object : MarsPhotosRepository {
            override suspend fun getMarsPhotos(): List<MarsPhoto> {
                if (fail) throw IOException("Offline")
                return FakeDataSource.photosList
            }
        })
        assertEquals(MarsUiState.Error, model.marsUiState)
        fail = false
        model.getMarsPhotos()
        assertEquals(MarsUiState.Success(FakeDataSource.photosList), model.marsUiState)
    }

    @Test fun malformedResponseProducesError() = runTest {
        val model = MarsViewModel(object : MarsPhotosRepository {
            override suspend fun getMarsPhotos(): List<MarsPhoto> = throw SerializationException("Bad JSON")
        })
        assertEquals(MarsUiState.Error, model.marsUiState)
    }
}
