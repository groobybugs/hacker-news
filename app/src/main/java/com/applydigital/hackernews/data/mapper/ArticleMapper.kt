package com.applydigital.hackernews.data.mapper

import com.applydigital.hackernews.data.remote.model.ArticleResponse
import com.applydigital.hackernews.domain.model.Article

fun ArticleResponse.toDomain(): Article {
    return Article(
        id = objectID,
        title = storyTitle ?: title,
        url = storyUrl,
        author = author,
        createdAt = createdAt,
        isDeleted = false
    )
}

