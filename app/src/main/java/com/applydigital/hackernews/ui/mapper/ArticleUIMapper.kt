package com.applydigital.hackernews.ui.mapper

import com.applydigital.hackernews.domain.model.Article
import com.applydigital.hackernews.ui.model.ArticleUi
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun Article.toUi(): ArticleUi {
    return ArticleUi(
        id = id,
        title = title,
        author = author,
        url = url,
        createdAt = createdAt,
        formattedTime = getFormattedTime(createdAt),
        isDeleted = isDeleted
    )
}

private fun getFormattedTime(createdAt: String): String {
    if (createdAt.isEmpty()) return ""
    val created = Instant.parse(createdAt).atZone(ZoneId.systemDefault()).toLocalDateTime()
    val now = LocalDateTime.now()
    val duration = Duration.between(created, now)

    return when {
        duration.toMinutes() < 60 -> "${duration.toMinutes()}m"
        duration.toHours() < 24 -> "${duration.toHours()}h"
        ChronoUnit.DAYS.between(created.toLocalDate(), now.toLocalDate()) == 1L -> "Yesterday"
        else -> "${ChronoUnit.DAYS.between(created.toLocalDate(), now.toLocalDate())}d"
    }
}
