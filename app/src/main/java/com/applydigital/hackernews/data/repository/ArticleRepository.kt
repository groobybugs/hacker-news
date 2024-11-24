package com.applydigital.hackernews.data.repository

import com.applydigital.hackernews.data.local.dao.ArticleDao
import com.applydigital.hackernews.data.remote.api.HackerNewsApi
import com.applydigital.hackernews.domain.model.Article
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ArticleRepository @Inject constructor(
    private val api: HackerNewsApi,
    private val articleDao: ArticleDao
) {
    fun getArticles(): Flow<List<Article>> {
        return flowOf()
    }

    suspend fun refreshArticles() {
        try {
            val response = api.getArticles()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteArticle(articleId: String) {
        articleDao.deleteArticle(articleId)
    }
}
