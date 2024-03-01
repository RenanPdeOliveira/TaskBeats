package com.comunidadedevspace.taskbeats.news.presentation

import com.comunidadedevspace.taskbeats.news.data.local.NewsItem

data class NewsState(
    val list: List<NewsItem>? = null,
    val allNews: List<NewsItem>? = null,
    val isLoading: Boolean = false
)
