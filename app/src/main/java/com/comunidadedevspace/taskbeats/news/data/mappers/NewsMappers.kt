package com.comunidadedevspace.taskbeats.news.data.mappers

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.news.data.remote.NewsDto

fun List<NewsDto>.toNewsItem(): List<NewsItem> {
    return map { dto ->
        NewsItem(
            id = dto.id,
            title = dto.title,
            imageUrl = dto.imageUrl,
            isFavorite = false,
            drawableResId = R.drawable.baseline_favorite_border_24
        )
    }
}