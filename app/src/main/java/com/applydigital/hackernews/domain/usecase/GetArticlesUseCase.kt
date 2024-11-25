package com.applydigital.hackernews.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.applydigital.hackernews.domain.model.Article
import com.applydigital.hackernews.domain.repository.ArticleRepository
import com.applydigital.hackernews.utils.constants.Constants.IO_DISPATCHER
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository,
    @Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
) {
    private val seenArticles = HashSet<String>()
    private val refresh = MutableSharedFlow<Int?>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<PagingData<Article>> =
        refresh
            .flatMapLatest {
                repository
                    .getArticles()
            }
            .map { pagingData ->
                pagingData
                    .also { seenArticles.clear() }
                    .filter { article ->
                        seenArticles.add(article.id)
                    }
            }
            .flowOn(ioDispatcher)

    suspend fun refreshArticles() =
        refresh.emit(null)
}
