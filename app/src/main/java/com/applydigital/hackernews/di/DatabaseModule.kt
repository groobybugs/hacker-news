package com.applydigital.hackernews.di

import android.content.Context
import androidx.room.Room
import com.applydigital.hackernews.data.local.dao.ArticleDao
import com.applydigital.hackernews.data.local.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            ArticleDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: ArticleDatabase): ArticleDao {
        return database.articleDao()
    }
}
