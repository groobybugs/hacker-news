package com.applydigital.hackernews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.applydigital.hackernews.data.local.dao.ArticleDao
import com.applydigital.hackernews.data.mapper.toDomain
import com.applydigital.hackernews.data.mapper.toEntity
import com.applydigital.hackernews.data.remote.HackerNewsApi
import com.applydigital.hackernews.data.remote.NetworkResult
import com.applydigital.hackernews.data.remote.paging.ArticlesDataSource
import com.applydigital.hackernews.data.remote.safeApiCall
import com.applydigital.hackernews.domain.model.Article
import com.applydigital.hackernews.domain.repository.ArticleRepository
import com.applydigital.hackernews.domain.service.NetworkConnectivity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val api: HackerNewsApi,
    private val articleDao: ArticleDao,
    private val networkConnectivity: NetworkConnectivity
) : ArticleRepository {

    override fun getArticles(): Flow<PagingData<Article>> =
        if (networkConnectivity.isNetworkAvailable()) {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 5,
                    initialLoadSize = 40
                ),
                pagingSourceFactory = { ArticlesDataSource(api, articleDao) }
            ).flow.map { pagingData ->
                pagingData.map { it.toDomain() }
            }
        } else {
            articleDao.getArticles().map { articles ->
                PagingData.from(articles.map { it.toDomain() })
            }
        }

    override suspend fun refreshArticles(): NetworkResult<Unit> = safeApiCall {
        val response = api.getArticles(page = 0, hitsPerPage = 20)
        val articles = response.hits.map { it.toEntity() }
        articleDao.insertArticles(articles)
    }

    override suspend fun deleteArticle(id: String) {
        articleDao.deleteArticle(id)
    }

    override fun getDeletedArticleIds(): Flow<Set<String>> = flow {
        val deletedArticles = articleDao.getDeletedArticles()
        emit(deletedArticles.map { it.id }.toSet())
    }
}
