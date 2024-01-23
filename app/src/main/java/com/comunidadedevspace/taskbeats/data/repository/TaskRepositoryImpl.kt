package com.comunidadedevspace.taskbeats.data.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.domain.TaskRepository

class TaskRepositoryImpl(
    private val dao: TaskDao
): TaskRepository {
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
}