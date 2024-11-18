package com.applydigital.hackernews.data.remote.model

import com.alibaba.fastjson2.annotation.JSONField

data class ArticleResponse(
    @JSONField(name = "objectID")val objectID: String = "",
    @JSONField(name = "story_title") val storyTitle: String? = null,
    @JSONField(name = "story_url") val storyUrl: String = "",
    @JSONField(name = "author") val author: String = "",
    @JSONField(name = "created_at") val createdAt: String = "",
    @JSONField(name = "title") val title: String = "",
)
