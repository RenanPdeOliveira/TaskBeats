package com.comunidadedevspace.taskbeats.news.domain.use_case

import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository

class DeleteNewsByIdUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(news: NewsDomain) {
        repository.deleteNewsById(news.id)
    }
}