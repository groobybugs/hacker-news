package com.applydigital.hackernews.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.applydigital.hackernews.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE is_deleted = 0 ORDER BY created_at DESC")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("UPDATE articles SET is_deleted = 1 WHERE id = :articleId")
    suspend fun deleteArticle(articleId: String)

    @Query("DELETE FROM articles WHERE is_deleted = 1")
    suspend fun deleteMarkedArticles()

    @Query("SELECT * FROM articles WHERE is_deleted = 1")
    suspend fun getDeletedArticles(): List<ArticleEntity>
}

