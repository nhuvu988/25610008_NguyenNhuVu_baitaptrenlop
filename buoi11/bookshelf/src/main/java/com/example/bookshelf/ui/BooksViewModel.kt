package com.example.bookshelf.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import com.google.gson.JsonParseException
import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface BooksUiState {
    data object Loading : BooksUiState
    data class Success(val books: List<Book>, val query: String) : BooksUiState
    data class Error(val message: String) : BooksUiState
}

class BooksViewModel(private val repository: BooksRepository) : ViewModel() {
    var uiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set
    var lastQuery: String = "android"
        private set
    private var request: Job? = null

    init { search(lastQuery) }

    fun search(query: String) {
        request?.cancel()
        lastQuery = query.trim()
        if (lastQuery.isBlank()) {
            uiState = BooksUiState.Error("Vui lòng nhập từ khóa tìm sách.")
            return
        }
        val requestedQuery = lastQuery
        request = viewModelScope.launch {
            uiState = BooksUiState.Loading
            uiState = try {
                BooksUiState.Success(repository.searchBooks(requestedQuery), requestedQuery)
            } catch (cancel: CancellationException) {
                throw cancel
            } catch (error: HttpException) {
                BooksUiState.Error(if (error.code() == 429 || error.code() == 403)
                    "Google Books từ chối yêu cầu hoặc đã hết hạn mức. Hãy thử lại sau hoặc cấu hình API key riêng."
                    else "Google Books gặp lỗi HTTP ${error.code()}. Vui lòng thử lại.")
            } catch (error: IOException) {
                BooksUiState.Error("Không kết nối được Google Books. Kiểm tra Internet và thử lại.")
            } catch (error: JsonParseException) {
                BooksUiState.Error("Dữ liệu sách không hợp lệ. Vui lòng thử lại.")
            }
        }
    }

    fun retry() = search(lastQuery)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication
                BooksViewModel(app.container.booksRepository)
            }
        }
    }
}
