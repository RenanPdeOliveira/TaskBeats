package com.comunidadedevspace.taskbeats.data.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.data.local.dao.NewsDao
import com.comunidadedevspace.taskbeats.data.local.entity.NewsItem
import com.comunidadedevspace.taskbeats.data.local.dao.TaskDao
import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem
import com.comunidadedevspace.taskbeats.data.mappers.toNewsItem
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.domain.util.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val newsDao: NewsDao,
    private val api: NewsService
) : TaskRepository {
    override suspend fun insert(task: TaskItem) {
        taskDao.insert(task)
    }

    override suspend fun delete(task: TaskItem) {
        taskDao.delete(task)
    }

    override suspend fun update(task: TaskItem) {
        taskDao.update(task)
    }

    override suspend fun deleteAll() {
        taskDao.deleteAll()
    }

    override fun getAll(): LiveData<List<TaskItem>> {
        return taskDao.getAll()
    }

    override suspend fun fetchTopNews(): Resource<List<NewsItem>> {
        return try {
            Resource.Success(data = api.fetchTopNews().data.toNewsItem())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Resource.Error(message = e.message ?: "Top news failed request")
        }
    }

    override suspend fun fetchAllNews(): Resource<List<NewsItem>> {
        return try {
            Resource.Success(data = api.fetchAllNews().data.toNewsItem())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Resource.Error(message = e.message ?: "All news failed request")
        }
    }

    override suspend fun insertNews(news: NewsItem) {
        newsDao.insertNews(news)
    }

    override suspend fun deleteNewsById(id: String) {
       newsDao.deleteNewsById(id)
    }

    override fun getAllNews(): Flow<List<NewsItem>> {
        return newsDao.getAllNews()
    }
}