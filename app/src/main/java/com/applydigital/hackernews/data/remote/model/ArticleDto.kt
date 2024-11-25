package com.applydigital.hackernews.data.remote.model

data class ArticleDto(
    val objectID: String,
    val storyTitle: String?,
    val title: String?,
    val storyUrl: String?,
    val url: String?,
    val author: String,
    val createdAt: String,
    var isDeleted: Boolean = false
) {
    val effectiveTitle: String
        get() = storyTitle ?: title ?: ""

    val effectiveUrl: String
        get() = storyUrl ?: url ?: ""
}
