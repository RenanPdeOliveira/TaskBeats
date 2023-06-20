package com.comunidadedevspace.taskbeats.data.remote

data class NewsResponse(
    val category: String,
    val data: List<NewsDto>
)

data class NewsDto(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String
)