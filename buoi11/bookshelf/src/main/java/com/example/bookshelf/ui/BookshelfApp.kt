package com.example.bookshelf.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.bookshelf.R
import com.example.bookshelf.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(state: BooksUiState, search: (String) -> Unit, retry: () -> Unit) {
    var query by rememberSaveable { mutableStateOf("android") }
    val focus = LocalFocusManager.current
    val submit = { focus.clearFocus(); search(query) }
    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = query, onValueChange = { query = it },
                    label = { Text(stringResource(R.string.search_label)) }, singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { submit() }), modifier = Modifier.weight(1f))
                Button(onClick = submit) { Text(stringResource(R.string.search)) }
            }
            BooksContent(state, retry, Modifier.weight(1f))
        }
    }
}

@Composable
fun BooksContent(state: BooksUiState, retry: () -> Unit, modifier: Modifier = Modifier) {
    when (state) {
        BooksUiState.Loading -> Column(modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text(stringResource(R.string.loading), Modifier.padding(16.dp))
        }
        is BooksUiState.Error -> Column(modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(state.message)
            Button(onClick = retry, modifier = Modifier.padding(top = 12.dp)) { Text(stringResource(R.string.retry)) }
        }
        is BooksUiState.Success -> if (state.books.isEmpty()) {
            Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { Text(stringResource(R.string.empty)) }
        } else Column(modifier) {
            Text(stringResource(R.string.results, state.books.size, state.query), Modifier.padding(horizontal = 16.dp))
            LazyVerticalGrid(columns = GridCells.Adaptive(160.dp), contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(state.books, key = { it.id }) { BookCard(it) }
            }
        }
    }
}

@Composable
private fun BookCard(book: Book) {
    Card(Modifier.fillMaxWidth()) {
        SubcomposeAsyncImage(model = book.thumbnail, contentDescription = stringResource(R.string.cover, book.title),
            modifier = Modifier.fillMaxWidth().aspectRatio(.7f), contentScale = ContentScale.Fit,
            loading = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(Modifier.size(28.dp)) } },
            error = { Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.missing_cover))
            } })
        Text(book.title, Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp), fontWeight = FontWeight.SemiBold,
            minLines = 2, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(book.authors.joinToString().ifBlank { "Chưa rõ tác giả" }, Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}
