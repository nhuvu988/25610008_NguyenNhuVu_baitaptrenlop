package com.example.bookshelf

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.BooksContent
import com.example.bookshelf.ui.BooksUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookshelfUiTest {
    @get:Rule val compose = createComposeRule()

    @Test fun successDisplaysBookTitlesWithoutAnApiKey() {
        val books = listOf(Book("1", "Kotlin in Action", listOf("Author One"), null),
            Book("2", "Android Basics", listOf("Author Two"), null))
        compose.setContent { MaterialTheme { BooksContent(BooksUiState.Success(books, "android"), {}) } }
        compose.onNodeWithText("Kotlin in Action").assertIsDisplayed()
        compose.onNodeWithText("Android Basics").assertIsDisplayed()
        compose.onAllNodesWithText("Chưa có bìa").assertCountEquals(2)
    }

    @Test fun errorOffersRetry() {
        var retries = 0
        compose.setContent { MaterialTheme { BooksContent(BooksUiState.Error("Không có mạng"), { retries++ }) } }
        compose.onNodeWithText("Không có mạng").assertIsDisplayed()
        compose.onNodeWithText("Thử lại").performClick()
        compose.runOnIdle { assertEquals(1, retries) }
    }

    @Test fun missingItemsDisplaysEmptyState() {
        compose.setContent { MaterialTheme { BooksContent(BooksUiState.Success(emptyList(), "unknown"), {}) } }
        compose.onNodeWithText("Không tìm thấy sách. Hãy thử từ khóa khác.").assertIsDisplayed()
    }
}
