package com.applydigital.hackernews.domain.model

data class Article(
    val id: String,
    val title: String,
    val author: String,
    val url: String,
    val createdAt: String,
    var isDeleted: Boolean = false
)
