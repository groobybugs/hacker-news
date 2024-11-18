package com.applydigital.hackernews.data.remote.model

data class HackerNewsResponse(
    val hits: List<ArticleResponse> = mutableListOf(),
    val nbHits: Int = 0,
    val page: Int = 0,
    val nbPages: Int = 0,
    val hitsPerPage: Int = 0,
)
