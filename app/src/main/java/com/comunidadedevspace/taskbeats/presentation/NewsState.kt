package com.comunidadedevspace.taskbeats.presentation

import com.comunidadedevspace.taskbeats.data.remote.NewsDto

data class NewsState(
    val list: List<NewsDto>? = null,
    val isLoading: Boolean = false
)
