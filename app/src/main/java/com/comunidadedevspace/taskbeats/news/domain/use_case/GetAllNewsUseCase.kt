package com.comunidadedevspace.taskbeats.news.domain.use_case

import com.comunidadedevspace.taskbeats.core.domain.util.Resource
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository

class GetAllNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Resource<List<NewsDomain>> {
        val result = repository.fetchAllNews()
        return result.data?.let {
            Resource.Success(data = result.data)
        } ?: Resource.Error(message = result.message)
    }
}