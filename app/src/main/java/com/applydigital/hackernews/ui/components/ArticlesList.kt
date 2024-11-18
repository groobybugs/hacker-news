package com.applydigital.hackernews.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.applydigital.hackernews.ui.model.ArticleUi

@Composable
fun ArticlesList(
    articles: LazyPagingItems<ArticleUi>,
    onArticleClick: (ArticleUi) -> Unit,
    onDeleteArticle: (ArticleUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(articles.loadState.refresh) {
        if (articles.loadState.refresh is LoadState.Loading) {
            listState.scrollToItem(0)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        if (articles.loadState.refresh is LoadState.Loading) {
            items(3) {
                ShimmerListElement(modifier)
                Spacer(modifier = Modifier.padding(top = 2.dp))
            }
        }

        items(
            count = articles.itemCount,
            key = { index -> articles[index]?.id ?: index }
        ) { index ->
            articles[index]?.let { article ->
                ArticleItem(
                    article = article,
                    onArticleClick = onArticleClick,
                    onDelete = onDeleteArticle
                )
            }
        }

        if (articles.loadState.append is LoadState.Loading) {
            items(6) {
                ShimmerListElement(modifier)
                Spacer(modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}



