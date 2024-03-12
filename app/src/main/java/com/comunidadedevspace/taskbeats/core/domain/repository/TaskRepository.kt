package com.comunidadedevspace.taskbeats.core.domain.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.core.domain.util.Resource
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(task: TaskItem)

    suspend fun delete(task: TaskItem)

    suspend fun update(task: TaskItem)

    suspend fun deleteAll()

    fun getAll(): LiveData<List<TaskItem>>

    suspend fun fetchTopNews(): Resource<List<NewsItem>>

    suspend fun fetchAllNews(): Resource<List<NewsItem>>

    suspend fun insertNews(news: NewsItem)

    suspend fun deleteNewsById(id: String)

    fun getAllNews(): Flow<List<NewsItem>>
}