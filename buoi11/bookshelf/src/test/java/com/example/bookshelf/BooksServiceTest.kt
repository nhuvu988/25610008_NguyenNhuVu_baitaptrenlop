package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.network.BooksApiService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BooksServiceTest {
    @Test fun retrofitParsesSearchAndDetailJsonIncludingEncodedQuery() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setBody("""{"items":[{"id":"id1"}],"totalItems":1}"""))
            server.enqueue(MockResponse().setBody("""{"id":"id1","volumeInfo":{"title":"Kotlin","authors":["Vũ"],"imageLinks":{"thumbnail":"http://example.test/cover.jpg"}}}"""))
            val service = Retrofit.Builder().baseUrl(server.url("/books/v1/"))
                .addConverterFactory(GsonConverterFactory.create()).build().create(BooksApiService::class.java)
            val book = NetworkBooksRepository(service).searchBooks("kotlin android").single()
            assertEquals("Kotlin", book.title)
            assertEquals("https://example.test/cover.jpg", book.thumbnail)
            val search = server.takeRequest().requestUrl!!
            assertEquals("/books/v1/volumes", search.encodedPath)
            assertEquals("kotlin android", search.queryParameter("q"))
            assertEquals("/books/v1/volumes/id1", server.takeRequest().path)
        }
    }

    @Test fun quotaErrorIsNotSilentlyTurnedIntoAnEmptySuccess() = runTest {
        MockWebServer().use { server ->
            server.enqueue(MockResponse().setResponseCode(429).setBody("{}"))
            val service = Retrofit.Builder().baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create()).build().create(BooksApiService::class.java)
            try {
                NetworkBooksRepository(service).searchBooks("android")
                fail("HTTP error must propagate")
            } catch (error: HttpException) {
                assertEquals(429, error.code())
            }
        }
    }
}
