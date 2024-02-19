package com.comunidadedevspace.taskbeats.presentation.events

import com.comunidadedevspace.taskbeats.data.local.NewsItem

sealed class NewsListEvents {
    data class OnAddFavorite(val news: NewsItem) : NewsListEvents()
    data class OnDeleteFavorite(val news: NewsItem) : NewsListEvents()
}