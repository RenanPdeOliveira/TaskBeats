package com.comunidadedevspace.taskbeats.news.domain.usecase

import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.domain.util.Resource
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem

class GetAllNewsUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Resource<List<NewsItem>> {
        val result = repository.fetchAllNews()
        return result.data?.let {
            Resource.Success(data = result.data)
        } ?: Resource.Error(message = result.message)
    }
}