package com.comunidadedevspace.taskbeats.news.domain.usecase

import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem

class DeleteNewsByIdUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(news: NewsItem) {
        repository.deleteNewsById(news.id)
    }
}