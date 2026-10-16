package com.example.bookshelf

import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.BooksUiState
import com.example.bookshelf.ui.BooksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class BooksViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    @Before fun setUp() { Dispatchers.setMain(dispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }

    @Test fun loadingThenSuccessAndEmptyResult() = runTest(dispatcher) {
        val model = BooksViewModel(object : BooksRepository {
            override suspend fun searchBooks(query: String) = emptyList<Book>()
        })
        assertEquals(BooksUiState.Loading, model.uiState)
        advanceUntilIdle()
        assertEquals(BooksUiState.Success(emptyList(), "android"), model.uiState)
    }

    @Test fun networkErrorCanRetrySameQuerySuccessfully() = runTest(dispatcher) {
        var failed = true
        val queries = mutableListOf<String>()
        val book = Book("1", "Kotlin", emptyList(), null)
        val model = BooksViewModel(object : BooksRepository {
            override suspend fun searchBooks(query: String): List<Book> {
                queries += query
                if (failed) throw IOException("Offline")
                return listOf(book)
            }
        })
        advanceUntilIdle()
        assertTrue(model.uiState is BooksUiState.Error)
        failed = false
        model.retry()
        advanceUntilIdle()
        assertEquals(listOf("android", "android"), queries)
        assertEquals(BooksUiState.Success(listOf(book), "android"), model.uiState)
    }

    @Test fun newerSearchCancelsOlderRequestInsteadOfOverwritingResults() = runTest(dispatcher) {
        val model = BooksViewModel(object : BooksRepository {
            override suspend fun searchBooks(query: String): List<Book> {
                delay(if (query == "android") 1_000 else 10)
                return listOf(Book(query, query, emptyList(), null))
            }
        })
        runCurrent()
        model.search("kotlin")
        advanceUntilIdle()
        assertEquals("kotlin", (model.uiState as BooksUiState.Success).query)
    }

    @Test fun quotaFailureExplainsProblemAndBlankInputDoesNotCallService() = runTest(dispatcher) {
        var calls = 0
        val model = BooksViewModel(object : BooksRepository {
            override suspend fun searchBooks(query: String): List<Book> {
                calls++
                throw HttpException(Response.error<Any>(429, "{}".toResponseBody("application/json".toMediaType())))
            }
        })
        advanceUntilIdle()
        assertTrue((model.uiState as BooksUiState.Error).message.contains("hạn mức"))
        model.search(" ")
        advanceUntilIdle()
        assertEquals(1, calls)
        assertTrue((model.uiState as BooksUiState.Error).message.contains("từ khóa"))
    }
}
