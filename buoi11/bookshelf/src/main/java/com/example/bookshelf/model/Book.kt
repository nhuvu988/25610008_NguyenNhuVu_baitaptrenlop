package com.example.bookshelf.model

// Các trường nullable vì Google Books có thể bỏ items, authors hoặc imageLinks.
data class SearchResponse(val items: List<Volume>? = null)
data class Volume(val id: String? = null, val volumeInfo: VolumeInfo? = null)
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null
)
data class ImageLinks(val thumbnail: String? = null, val smallThumbnail: String? = null)

data class Book(val id: String, val title: String, val authors: List<String>, val thumbnail: String?)

fun Volume.toBook(): Book? {
    val bookId = id?.takeIf { it.isNotBlank() } ?: return null
    val image = volumeInfo?.imageLinks?.let { it.thumbnail ?: it.smallThumbnail }
    val secureImage = when {
        image?.startsWith("http://") == true -> "https://" + image.removePrefix("http://")
        image?.startsWith("https://") == true -> image
        else -> null
    }
    return Book(bookId, volumeInfo?.title?.takeIf { it.isNotBlank() } ?: "Chưa có tiêu đề",
        volumeInfo?.authors.orEmpty(), secureImage)
}
