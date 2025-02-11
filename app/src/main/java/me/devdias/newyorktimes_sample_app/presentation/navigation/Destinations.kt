package me.devdias.newyorktimes_sample_app.presentation.navigation

import androidx.navigation.NavController
import java.net.URLEncoder

sealed class Destinations(
    val route: String,
    val routeWithArgs: String = route
) {
    object TopStories : Destinations("top_stories")

    object ArticleDetail : Destinations(
        route = "article_detail",
        routeWithArgs = "article_detail/{url}"
    ) {
        const val URL_ARG = "url"  // Define the argument name

        // Helper function to create the full route
        fun createRoute(url: String): String {
            return "article_detail/${URLEncoder.encode(url, "UTF-8")}"
        }
    }
}
