package me.devdias.newyorktimes_sample_app.presentation.top_stories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.devdias.newyorktimes_sample_app.domain.model.Result
import me.devdias.newyorktimes_sample_app.domain.model.Article
import me.devdias.newyorktimes_sample_app.domain.usecase.GetTopStoriesUseCase
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesEvent
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesSideEffect
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesState
import javax.inject.Inject

@HiltViewModel
class TopStoriesViewModel @Inject constructor(
    private val getTopStoriesUseCase: GetTopStoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TopStoriesState())
    val state: StateFlow<TopStoriesState> = _state.asStateFlow()

    private val _sideEffect = Channel<TopStoriesSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun handleEvent(event: TopStoriesEvent) {
        when (event) {
            is TopStoriesEvent.LoadTopStories -> loadTopStories()
            is TopStoriesEvent.OnArticleClick -> handleArticleClick(event.article)
        }
    }

    private fun loadTopStories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getTopStoriesUseCase()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            articles = result.data,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
    private fun handleArticleClick(article: Article) {
        viewModelScope.launch {
            _sideEffect.send(TopStoriesSideEffect.NavigateToArticleDetail(article.url))
        }
    }
}