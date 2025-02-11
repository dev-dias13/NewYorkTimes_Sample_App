package me.devdias.newyorktimes_sample_app.presentation.top_stories.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.devdias.newyorktimes_sample_app.domain.model.Article
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.devdias.newyorktimes_sample_app.R
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesEvent
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesSideEffect
import me.devdias.newyorktimes_sample_app.presentation.top_stories.state.TopStoriesState
import me.devdias.newyorktimes_sample_app.presentation.top_stories.viewmodel.TopStoriesViewModel

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Section
            article.imageUrl?.let { url ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    contentDescription = article.title,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.placeholder_image),
                    error = painterResource(R.drawable.error_image)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Text Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.abstract,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Published: ${article.publishedDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
        ArticleItem(
            article = Article(
                title = "Sample Article Title That Might Be Longer Than Expected",
                abstract = "This is a sample article abstract that demonstrates how the text will look when it spans multiple lines. It should properly truncate with ellipsis if too long.",
                url = "https://example.com",
                publishedDate = "2024-03-20",
                imageUrl = "https://via.placeholder.com/150"
            ),
            onClick = {}
        )
}

@Preview(showBackground = true)
@Composable
fun ArticleItemWithoutImagePreview() {
        ArticleItem(
            article = Article(
                title = "Article Without Image",
                abstract = "This article doesn't have an associated image, demonstrating the layout adjustment.",
                url = "https://example.com",
                publishedDate = "2024-03-20",
                imageUrl = null
            ),
            onClick = {}
        )
}