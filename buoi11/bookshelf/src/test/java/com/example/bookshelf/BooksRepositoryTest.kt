package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.model.*
import com.example.bookshelf.network.BooksApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class BooksRepositoryTest {
    @Test fun searchesIdsThenFetchesDetailsInOrderAndUpgradesImageUrls() = runTest {
        val requested = mutableListOf<String>()
        var active = 0
        var maximumActive = 0
        val api = object : BooksApiService {
            override suspend fun searchVolumes(query: String, limit: Int): SearchResponse {
                assertEquals("kotlin", query)
                return SearchResponse((1..8).map { Volume("$it") } + Volume("1") + Volume(null))
            }
            override suspend fun getVolume(id: String): Volume {
                requested += id
                active++
                maximumActive = maxOf(maximumActive, active)
                delay((10 - id.toInt()).toLong())
                active--
                return Volume(id, VolumeInfo("Book $id", listOf("Author"), ImageLinks("http://example.test/$id.jpg")))
            }
        }
        val books = NetworkBooksRepository(api).searchBooks(" kotlin ")
        assertEquals((1..8).map { "$it" }, books.map { it.id })
        assertEquals(8, requested.size)
        assertTrue(maximumActive in 2..4)
        assertEquals("https://example.test/1.jpg", books.first().thumbnail)
    }

    @Test fun missingItemsReturnsEmptyWithoutDetailRequests() = runTest {
        val api = object : BooksApiService {
            override suspend fun searchVolumes(query: String, limit: Int) = SearchResponse()
            override suspend fun getVolume(id: String): Volume = error("No detail requests expected")
        }
        assertTrue(NetworkBooksRepository(api).searchBooks("unknown").isEmpty())
    }

    @Test fun mapsMissingFieldsAndRejectsNonHttpImageSchemes() {
        assertNull(Volume().toBook())
        assertEquals(Book("id", "Chưa có tiêu đề", emptyList(), null), Volume("id").toBook())
        assertNull(Volume("id", VolumeInfo(imageLinks = ImageLinks("file:///tmp/image"))).toBook()!!.thumbnail)
        assertEquals("https://example.test/small", Volume("id", VolumeInfo(
            imageLinks = ImageLinks(smallThumbnail = "http://example.test/small"))).toBook()!!.thumbnail)
    }
}
