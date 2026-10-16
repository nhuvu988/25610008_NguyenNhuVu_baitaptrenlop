package com.example.bookshelf.data

import com.example.bookshelf.BuildConfig
import com.example.bookshelf.network.BooksApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer { val booksRepository: BooksRepository }

class DefaultAppContainer : AppContainer {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val original = chain.request()
            val request = if (BuildConfig.GOOGLE_BOOKS_API_KEY.isBlank()) original else {
                val url = original.url.newBuilder()
                    .addQueryParameter("key", BuildConfig.GOOGLE_BOOKS_API_KEY).build()
                original.newBuilder().url(url).build()
            }
            chain.proceed(request)
        }.build()

    private val api: BooksApiService by lazy {
        Retrofit.Builder().baseUrl("https://www.googleapis.com/books/v1/")
            .client(client).addConverterFactory(GsonConverterFactory.create()).build()
            .create(BooksApiService::class.java)
    }
    override val booksRepository: BooksRepository by lazy { NetworkBooksRepository(api) }
}
