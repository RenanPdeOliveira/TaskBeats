package com.comunidadedevspace.taskbeats.news.domain.use_case

import com.comunidadedevspace.taskbeats.core.domain.util.DataError
import com.comunidadedevspace.taskbeats.core.domain.util.Result
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository

class GetAllNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): Result<List<NewsDomain>, DataError.Network> {
        return when (val result = repository.fetchAllNews()) {
            is Result.Error -> {
                Result.Error(error = result.error)
            }
            is Result.Success -> {
                Result.Success(data = result.data)
            }
        }
    }
}