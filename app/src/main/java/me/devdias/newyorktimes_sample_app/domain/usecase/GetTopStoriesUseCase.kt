package me.devdias.newyorktimes_sample_app.domain.usecase


import me.devdias.newyorktimes_sample_app.domain.model.Article
import me.devdias.newyorktimes_sample_app.domain.repository.TopStoriesRepository
import me.devdias.newyorktimes_sample_app.domain.model.Result
import javax.inject.Inject

class GetTopStoriesUseCase @Inject constructor(
    private val repository: TopStoriesRepository
) {
    suspend operator fun invoke(): Result<List<Article>> {
        return repository.getTopStories()
    }
}