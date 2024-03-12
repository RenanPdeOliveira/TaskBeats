package com.comunidadedevspace.taskbeats.news.domain.usecase

import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteNewsUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<NewsItem>> {
        return repository.getAllNews()
    }
}