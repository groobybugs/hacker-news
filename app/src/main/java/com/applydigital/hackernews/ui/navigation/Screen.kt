package com.applydigital.hackernews.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Articles : Screen("articles")
    data object ArticleWeb : Screen("article_web/{url}") {
        fun createRoute(url: String) = "article_web/${Uri.encode(url)}"
    }
}
