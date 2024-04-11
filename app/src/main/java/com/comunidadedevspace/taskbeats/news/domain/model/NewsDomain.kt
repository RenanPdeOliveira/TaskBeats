package com.comunidadedevspace.taskbeats.news.domain.model

data class NewsDomain(
    val id: String,
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val drawableResId: Int
)
