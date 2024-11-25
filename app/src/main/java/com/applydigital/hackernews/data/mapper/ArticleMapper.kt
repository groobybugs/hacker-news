package com.applydigital.hackernews.data.mapper

import com.applydigital.hackernews.data.remote.model.ArticleDto
import com.applydigital.hackernews.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        id = objectID,
        title = effectiveTitle,
        url = effectiveUrl,
        author = author,
        createdAt = createdAt,
        isDeleted = isDeleted
    )
}

fun Article.toDto(): ArticleDto {
    return ArticleDto(
        objectID = id,
        storyTitle = title,
        title = null,
        storyUrl = url,
        url = null,
        author = author,
        createdAt = createdAt,
        isDeleted = isDeleted
    )
}
