package com.applydigital.hackernews.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.applydigital.hackernews.data.local.dao.ArticleDao
import com.applydigital.hackernews.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "article_database"
    }
}
