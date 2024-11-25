package com.applydigital.hackernews.data.remote.model

data class HackerNewsResponse(
    val hits: List<ArticleDto>,
    val nbHits: Int,
    val page: Int,
    val nbPages: Int,
    val hitsPerPage: Int,
    val exhaustiveNbHits: Boolean,
    val exhaustiveTypo: Boolean,
    val query: String,
    val params: String,
    val processingTimeMS: Int
)
