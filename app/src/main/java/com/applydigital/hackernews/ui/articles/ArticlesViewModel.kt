package com.applydigital.hackernews.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.applydigital.hackernews.domain.usecase.DeleteArticleUseCase
import com.applydigital.hackernews.domain.usecase.GetArticlesUseCase
import com.applydigital.hackernews.ui.mapper.toUi
import com.applydigital.hackernews.utils.constants.Constants.IO_DISPATCHER
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    @Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val deletedArticleId = MutableStateFlow<Set<String>>(mutableSetOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles = getArticlesUseCase()
        .cachedIn(viewModelScope)
        .map { pagingData ->
            deletedArticleId.emit(mutableSetOf())
            pagingData.map { article ->
                article.toUi()
            }
        }
        .flatMapLatest { pagingData ->
            deletedArticleId.map { deletedArticleId ->
                pagingData.filter { article ->
                    article.id !in deletedArticleId
                }
            }
        }
        .flowOn(ioDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun refreshArticles() {
        viewModelScope.launch {
            getArticlesUseCase.refreshArticles()
        }
    }

    fun deleteArticle(articleId: String) {
        viewModelScope.launch {
            deletedArticleId.update { it + articleId }
            deleteArticleUseCase(articleId)
        }
    }
}
