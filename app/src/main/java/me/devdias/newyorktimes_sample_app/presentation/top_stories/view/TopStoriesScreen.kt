package me.devdias.newyorktimes_sample_app.presentation.top_stories.view

import androidx.annotation.FloatRange
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.channels.Channel
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import me.devdias.newyorktimes_sample_app.domain.model.Article
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesEvent
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesSideEffect
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesState
import me.devdias.newyorktimes_sample_app.presentation.top_stories.viewmodel.TopStoriesViewModel

@Composable
fun TopStoriesScreen(
    viewModel: TopStoriesViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is TopStoriesSideEffect.NavigateToArticleDetail -> {
                    onNavigateToDetail(effect.url)
                }
            }
        }
    }

    TopStoriesContent(
        state = state,
        onEvent = viewModel::handleEvent
    )
}

@Composable
private fun TopStoriesContent(
    state: TopStoriesState,
    onEvent: (TopStoriesEvent) -> Unit
) {
    LazyColumn {
        items(state.articles) { article ->
            ArticleItem(
                article = article,
                onClick = { onEvent(TopStoriesEvent.OnArticleClick(article)) }
            )
        }
    }
}

@Composable
private fun ArticleList(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit
) {
    LazyColumn {
        items(articles) { article ->
            ArticleItem(
                article = article,
                onClick = { onArticleClick(article) }
            )
        }
    }
}

@Composable
private fun ErrorMessage(error: String) {
    println(error)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No articles found",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ShimmerList() {
    LazyColumn {
        items(10) {
            ShimmerListItem()
        }
    }
}


@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    @FloatRange(from = 0.0, to = 1.0) intensity: Float = 0.2f
) {
    val shimmer = remember { Shimmer.AlphaHighlightBuilder() }
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = intensity),
        Color.LightGray.copy(alpha = intensity * 0.5f),
        Color.LightGray.copy(alpha = intensity)
    )

    AndroidView(
        factory = { context ->
            ShimmerFrameLayout(context).apply {
                setShimmer(shimmer
                    .setDuration(1000)
                    .setBaseAlpha(0.7f)
                    .setHighlightAlpha(0.6f)
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(false)
                    .build()
                )
            }
        },
        modifier = modifier,
        update = { shimmerFrameLayout ->
            if (isVisible) {
                shimmerFrameLayout.startShimmer()
            } else {
                shimmerFrameLayout.stopShimmer()
            }
        }
    )
}

@Composable
fun ShimmerListItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image placeholder
        ShimmerEffect(
            modifier = Modifier
                .size(120.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Title placeholder
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Abstract placeholder
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date placeholder
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(16.dp)
                    .clip(MaterialTheme.shapes.small)
            )
        }
    }
}
