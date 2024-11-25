package com.applydigital.hackernews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.applydigital.hackernews.ui.articles.ArticlesScreen
import com.applydigital.hackernews.ui.articles.ArticlesViewModel

@Composable
fun HackerNewsNavigation(
    startDestination: String = Screen.Articles.route,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Articles.route) {
            val viewModel: ArticlesViewModel = hiltViewModel()
            ArticlesScreen(
                viewModel = viewModel,
                onArticleClick = { articleUrl ->
                    navController.navigate(Screen.ArticleWeb.createRoute(articleUrl))
                }
            )
        }

        composable(
            route = Screen.ArticleWeb.route,
            arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: return@composable
            //ArticleWebScreen(url = Uri.decode(url))
        }
    }
}
