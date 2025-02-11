package me.devdias.newyorktimes_sample_app.domain.model

data class Article(
    val title: String,
    val abstract: String,
    val url: String,
    val publishedDate: String,
    val imageUrl: String?
)