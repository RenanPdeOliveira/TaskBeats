package com.comunidadedevspace.taskbeats.news.data.mappers

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.news.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun List<NewsDto>.toNewsDomain(): List<NewsDomain> {
    return map { dto ->
        NewsDomain(
            id = dto.id,
            title = dto.title,
            imageUrl = dto.imageUrl,
            isFavorite = false,
            drawableResId = R.drawable.baseline_favorite_border_24
        )
    }
}

fun Flow<List<NewsItem>>.toNewsDomain(): Flow<List<NewsDomain>> {
    return map { list ->
        list.map { domain ->
            NewsDomain(
                id = domain.id,
                title = domain.title,
                imageUrl = domain.imageUrl,
                isFavorite = domain.isFavorite,
                drawableResId = domain.drawableResId
            )
        }
    }
}

fun NewsDomain.toNewsItem(): NewsItem {
    return NewsItem(
        id = id,
        title = title,
        imageUrl = imageUrl,
        isFavorite = isFavorite,
        drawableResId = drawableResId
    )
}