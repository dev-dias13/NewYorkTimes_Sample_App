package me.devdias.newyorktimes_sample_app.domain.repository

import me.devdias.newyorktimes_sample_app.domain.model.Article
import me.devdias.newyorktimes_sample_app.domain.model.Result

interface TopStoriesRepository {
    suspend fun getTopStories(): Result<List<Article>>
}