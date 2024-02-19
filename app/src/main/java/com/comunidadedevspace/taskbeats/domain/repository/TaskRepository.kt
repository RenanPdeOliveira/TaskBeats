package com.comunidadedevspace.taskbeats.domain.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.data.local.NewsItem
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(task: TaskItem)

    suspend fun delete(task: TaskItem)

    suspend fun update(task: TaskItem)

    suspend fun deleteAll()

    fun getAll(): LiveData<List<TaskItem>>

    suspend fun fetchTopNews(): Resource<NewsResponse>

    suspend fun fetchAllNews(): Resource<NewsResponse>

    suspend fun insertNews(news: NewsItem)

    suspend fun deleteNewsById(id: String)

    fun getAllNews(): Flow<List<NewsItem>>
}