package com.applydigital.hackernews.data.remote.api

import com.applydigital.hackernews.data.remote.model.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsApi {
    @GET("search_by_date")
    suspend fun getArticles(
        @Query("query") query: String = "mobile"
    ): ArticleResponse
}
