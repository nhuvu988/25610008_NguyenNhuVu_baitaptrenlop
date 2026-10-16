package com.example.amphibians

import com.example.amphibians.data.DefaultAmphibiansRepository
import com.example.amphibians.network.AmphibiansApiService
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AmphibiansNetworkTest {
    @Test fun parsesSnakeCaseImageAndIgnoresNewApiFields() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("""[{"name":"Frog","type":"Frog","description":"Green frog","img_src":"https://example.test/frog.png","extra":true}]"""))
            val service = Retrofit.Builder().baseUrl(server.url("/"))
                .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
                .build().create(AmphibiansApiService::class.java)
            val result = DefaultAmphibiansRepository(service).getAmphibians().single()
            assertEquals("Frog", result.name)
            assertEquals("https://example.test/frog.png", result.imgSrc)
            assertEquals("/amphibians", server.takeRequest().path)
        }
    }
}
