package com.comunidadedevspace.taskbeats.data.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.domain.util.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepositoryImpl(
    private val dao: TaskDao,
    private val api: NewsService
) : TaskRepository {
    override suspend fun insert(task: TaskItem) {
        dao.insert(task)
    }

    override suspend fun delete(task: TaskItem) {
        dao.delete(task)
    }

    override suspend fun update(task: TaskItem) {
        dao.update(task)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun getAll(): LiveData<List<TaskItem>> {
        return dao.getAll()
    }

    override suspend fun fetchTopNews(): Resource<NewsResponse> {
        return try {
            Resource.Success(data = api.fetchTopNews())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Resource.Error(message = e.message ?: "Top news failed request")
        }
    }

    override suspend fun fetchAllNews(): Resource<NewsResponse> {
        return try {
            Resource.Success(data = api.fetchAllNews())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Resource.Error(message = e.message ?: "All news failed request")
        }
    }
}