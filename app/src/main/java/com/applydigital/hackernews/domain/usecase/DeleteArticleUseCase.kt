package com.applydigital.hackernews.domain.usecase

import com.applydigital.hackernews.domain.repository.ArticleRepository
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(articleId: String) {
        repository.deleteArticle(articleId)
    }
}
