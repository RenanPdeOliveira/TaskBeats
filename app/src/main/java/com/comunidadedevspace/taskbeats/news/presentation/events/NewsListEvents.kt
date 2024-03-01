package com.comunidadedevspace.taskbeats.news.presentation.events

import com.comunidadedevspace.taskbeats.news.data.local.NewsItem

sealed class NewsListEvents {
    data class OnAddFavorite(val news: NewsItem) : NewsListEvents()
    data class OnDeleteFavorite(val news: NewsItem) : NewsListEvents()
}