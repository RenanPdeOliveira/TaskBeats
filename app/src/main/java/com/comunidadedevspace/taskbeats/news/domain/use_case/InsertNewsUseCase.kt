package com.comunidadedevspace.taskbeats.news.domain.use_case

import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository

class InsertNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(news: NewsDomain) {
        repository.insertNews(news)
    }
}