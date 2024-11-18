package com.applydigital.hackernews.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.applydigital.hackernews.data.local.dao.ArticleDao
import com.applydigital.hackernews.data.mapper.toEntity
import com.applydigital.hackernews.data.remote.HackerNewsApi
import com.applydigital.hackernews.data.remote.model.ArticleResponse

class ArticlesDataSource(
    private val api: HackerNewsApi,
    private val articleDao: ArticleDao,
) : PagingSource<Int, ArticleResponse>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleResponse>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleResponse> {
        return try {
            val page = params.key ?: 0
            val deletedArticles = articleDao.getDeletedArticles()
            val deletedIds = deletedArticles.map { it.id }.toSet()

            val response = api.getArticles(
                page = page,
                hitsPerPage = params.loadSize
            )

            // Filter out deleted articles and cache the new ones
            val filteredArticles = response.hits.filterNot {
                deletedIds.contains(it.objectID) || it.storyUrl.isEmpty()
            }
            articleDao.insertArticles(filteredArticles.map { it.toEntity() })

            LoadResult.Page(
                data = filteredArticles,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (filteredArticles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
