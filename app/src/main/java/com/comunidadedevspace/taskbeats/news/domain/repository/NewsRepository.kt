package com.comunidadedevspace.taskbeats.news.domain.repository

import com.comunidadedevspace.taskbeats.core.domain.util.DataError
import com.comunidadedevspace.taskbeats.core.domain.util.Resource
import com.comunidadedevspace.taskbeats.core.domain.util.Result
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun fetchTopNews(): Result<List<NewsDomain>, DataError.Network>

    suspend fun fetchAllNews(): Resource<List<NewsDomain>>

    suspend fun insertNews(news: NewsDomain)

    suspend fun deleteNewsById(id: String)

    fun getAllNews(): Flow<List<NewsDomain>>
}