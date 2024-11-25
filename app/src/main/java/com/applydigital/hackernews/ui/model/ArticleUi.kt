package com.applydigital.hackernews.ui.model

data class ArticleUi(
    val id: String,
    val title: String,
    val author: String,
    val url: String,
    val createdAt: String,
    val formattedTime: String,
    var isDeleted: Boolean = false
)
