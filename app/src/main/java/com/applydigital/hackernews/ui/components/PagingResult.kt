package com.applydigital.hackernews.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> handlePagingResult(
    modifier: Modifier = Modifier,
    items: LazyPagingItems<T>,
): Boolean {
    val loadState = items.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect(modifier)
            false
        }

        loadState.refresh is LoadState.NotLoading && items.itemCount == 0 -> {
            false
        }

        error != null -> {
            false
        }

        else -> {
            true
        }
    }
}
