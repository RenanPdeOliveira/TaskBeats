package com.comunidadedevspace.taskbeats.news.presentation.news_list

import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain

sealed class NewsListEvents {
    data class OnAddFavorite(val news: NewsDomain) : NewsListEvents()
    data class OnDeleteFavorite(val news: NewsDomain) : NewsListEvents()
}