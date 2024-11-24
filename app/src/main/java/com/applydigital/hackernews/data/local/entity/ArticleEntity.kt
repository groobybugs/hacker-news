package com.applydigital.hackernews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val url: String,
    val createdAt: String,
    val isDeleted: Boolean = false
)
