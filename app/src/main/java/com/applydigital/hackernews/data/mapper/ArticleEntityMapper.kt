package com.applydigital.hackernews.data.mapper

import com.applydigital.hackernews.data.local.entity.ArticleEntity
import com.applydigital.hackernews.data.remote.model.ArticleResponse
import com.applydigital.hackernews.domain.model.Article

fun ArticleEntity.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        url = url,
        author = author,
        createdAt = createdAt,
        isDeleted = isDeleted
    )
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        url = url,
        author = author,
        createdAt = createdAt,
        isDeleted = isDeleted
    )
}

fun ArticleResponse.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = objectID,
        title = storyTitle ?: title,
        url = storyUrl,
        author = author,
        createdAt = createdAt,
        isDeleted = false
    )
}
