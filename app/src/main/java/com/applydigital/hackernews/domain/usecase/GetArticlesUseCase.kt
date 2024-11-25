package com.applydigital.hackernews.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.applydigital.hackernews.domain.model.Article
import com.applydigital.hackernews.domain.repository.ArticleRepository
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    private val seenArticles = HashSet<String>()
    private val refresh = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<PagingData<Article>> =
        refresh
            .flatMapLatest {
                repository
                    .also { seenArticles.clear() }
                    .getArticles()
            }
            .map { pagingData ->
                pagingData.filter { article ->
                    seenArticles.add(article.id)
                }
            }

    suspend fun refreshArticles() =
        refresh.emit(Unit)
}
