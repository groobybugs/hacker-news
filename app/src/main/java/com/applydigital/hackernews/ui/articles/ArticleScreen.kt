package com.applydigital.hackernews.ui.articles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.applydigital.hackernews.ui.components.ArticlesList
import com.applydigital.hackernews.ui.components.EmptyContent
import com.applydigital.hackernews.ui.components.ErrorContent
import com.applydigital.hackernews.utils.isNetworkAvailable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticlesViewModel,
    onArticleClick: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val articles = viewModel.articles.collectAsLazyPagingItems()
    val refreshing = articles.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.refreshArticles()
    }
    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        isRefreshing = refreshing,
        onRefresh = {
            coroutineScope.launch {
                if (!isNetworkAvailable(context)) pullRefreshState.animateToHidden()
                viewModel.refreshArticles()
            }
        },
        state = pullRefreshState,
    ) {
        when {
            articles.loadState.refresh is LoadState.Error -> {
                ErrorContent(
                    onRetryClick = { articles.retry() }
                )
            }
            articles.itemCount == 0 && !refreshing -> {
                EmptyContent()
            }
            else -> {
                ArticlesList(
                    articles = articles,
                    onArticleClick = { article -> onArticleClick(article.url) },
                    onDeleteArticle = { article -> viewModel.deleteArticle(article.id) },
                    modifier = modifier
                )
            }
        }
    }
}
