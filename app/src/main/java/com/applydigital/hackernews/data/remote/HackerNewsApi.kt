package com.applydigital.hackernews.data.remote

import com.applydigital.hackernews.data.remote.model.HackerNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsApi {
    @GET("search_by_date")
    suspend fun getArticles(
        @Query("query") query: String = "mobile",
        @Query("page") page: Int = 0,
        @Query("hitsPerPage") hitsPerPage: Int = 20
    ): HackerNewsResponse
}
