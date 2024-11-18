package com.applydigital.hackernews.domain.repository

import androidx.paging.PagingData
import com.applydigital.hackernews.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<PagingData<Article>>
    suspend fun deleteArticle(id: String)
    fun getDeletedArticleIds(): Flow<Set<String>>
}
