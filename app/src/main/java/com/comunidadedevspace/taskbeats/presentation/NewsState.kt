package com.comunidadedevspace.taskbeats.presentation

import com.comunidadedevspace.taskbeats.data.local.entity.NewsItem

data class NewsState(
    val list: List<NewsItem>? = null,
    val allNews: List<NewsItem>? = null,
    val isLoading: Boolean = false
)
