package com.applydigital.hackernews.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.applydigital.hackernews.domain.model.Article
import com.applydigital.hackernews.domain.usecase.GetArticlesUseCase
import com.applydigital.hackernews.ui.mapper.toUi
import com.applydigital.hackernews.utils.constants.Constants.IO_DISPATCHER
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    @Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val articles = getArticlesUseCase()
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { article ->
                article.toUi()
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

        }
    }
}
