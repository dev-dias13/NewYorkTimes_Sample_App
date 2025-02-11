package me.devdias.newyorktimes_sample_app.data.repository

import me.devdias.newyorktimes_sample_app.data.remote.ArticleResponse
import me.devdias.newyorktimes_sample_app.data.remote.NyTimesApiService
import me.devdias.newyorktimes_sample_app.domain.model.Article
import me.devdias.newyorktimes_sample_app.domain.repository.TopStoriesRepository
import me.devdias.newyorktimes_sample_app.domain.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStoriesRepositoryImpl @Inject constructor(
    private val apiService: NyTimesApiService
) : TopStoriesRepository {
    override suspend fun getTopStories(): Result<List<Article>> {
        return try {
            val response = apiService.getTopStories("z7m8gRqLQWd2yGPWPOpYFNpFemX4XciH")
            if (response.isSuccessful) {
                Result.Success(response.body()?.results?.map { it.toDomain() } ?: emptyList())
            } else {
                Result.Error("API error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Network error: ${e.message}", e)
        }
    }

}

private fun ArticleResponse.toDomain(): Article {
    return Article(
        title = title,
        abstract = abstract,
        url = url,
        publishedDate = published_date,
        imageUrl = multimedia?.firstOrNull { it.format == "superJumbo" }?.url
    )
}