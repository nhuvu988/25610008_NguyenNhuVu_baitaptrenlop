package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.toBook
import com.example.bookshelf.network.BooksApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

interface BooksRepository {
    suspend fun searchBooks(query: String): List<Book>
}

class NetworkBooksRepository(private val api: BooksApiService) : BooksRepository {
    override suspend fun searchBooks(query: String): List<Book> = coroutineScope {
        if (query.isBlank()) return@coroutineScope emptyList()
        val ids = api.searchVolumes(query.trim()).items.orEmpty()
            .mapNotNull { it.id?.takeIf(String::isNotBlank) }.distinct().take(20)
        // Bước 1 lấy ID; bước 2 lấy chi tiết từng sách theo đề bài. Tối đa 4 request đồng thời.
        val permits = Semaphore(4)
        ids.map { id -> async { permits.withPermit { api.getVolume(id).toBook() } } }
            .awaitAll().filterNotNull()
    }
}
