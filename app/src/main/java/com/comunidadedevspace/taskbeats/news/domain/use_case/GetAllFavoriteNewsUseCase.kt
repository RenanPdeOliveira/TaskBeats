package com.comunidadedevspace.taskbeats.news.domain.use_case

import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<NewsDomain>> {
        return repository.getAllNews()
    }
}