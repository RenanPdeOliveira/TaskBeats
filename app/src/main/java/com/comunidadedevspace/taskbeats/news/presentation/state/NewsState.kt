package com.comunidadedevspace.taskbeats.news.presentation.state

import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain

data class NewsState(
    val topNews: List<NewsDomain>? = null,
    val allNews: List<NewsDomain>? = null,
    val isLoading: Boolean = false
)
