package me.devdias.newyorktimes_sample_app.presentation.top_stories.state

import me.devdias.newyorktimes_sample_app.domain.model.Article

data class TopStoriesState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)

sealed interface TopStoriesEvent {
    data object LoadTopStories : TopStoriesEvent
    data class OnArticleClick(val article: Article) : TopStoriesEvent
}

sealed interface TopStoriesSideEffect {
    data class NavigateToArticleDetail(val url: String) : TopStoriesSideEffect
}