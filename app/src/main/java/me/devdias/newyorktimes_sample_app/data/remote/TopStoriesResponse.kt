package me.devdias.newyorktimes_sample_app.data.remote

data class TopStoriesResponse(
    val results: List<ArticleResponse>
)

data class ArticleResponse(
    val title: String,
    val abstract: String,
    val url: String,
    val published_date: String,
    val multimedia: List<MediaResponse>?
)

data class MediaResponse(
    val url: String,
    val format: String,
    val height: Int? = null,
    val width: Int? = null,
    val type: String? = null,
    val subtype: String? = null,
    val caption: String? = null,
    val copyright: String? = null
)