package me.devdias.newyorktimes_sample_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import me.devdias.newyorktimes_sample_app.presentation.navigation.Destinations
import me.devdias.newyorktimes_sample_app.presentation.top_stories.view.ArticleDetailScreen
import me.devdias.newyorktimes_sample_app.presentation.top_stories.view.TopStoriesScreen
import me.devdias.newyorktimes_sample_app.utils.NavigationUtils.toArticle
import java.net.URLDecoder

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.TopStories.route
    ) {
        composable(Destinations.TopStories.route) {
            TopStoriesScreen(
                onNavigateToDetail = { url ->
                    navController.navigate(
                        Destinations.ArticleDetail.createRoute(url)
                    )
                }
            )
        }

        composable(
            route = Destinations.ArticleDetail.routeWithArgs,
            arguments = listOf(
                navArgument(Destinations.ArticleDetail.URL_ARG) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString(
                Destinations.ArticleDetail.URL_ARG
            )
            val url = URLDecoder.decode(encodedUrl ?: "", "UTF-8")

            ArticleDetailScreen(url)
        }
    }
}

