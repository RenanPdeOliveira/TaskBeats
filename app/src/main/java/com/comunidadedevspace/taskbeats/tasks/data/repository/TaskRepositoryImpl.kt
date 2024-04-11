package com.comunidadedevspace.taskbeats.tasks.data.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskDao
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.tasks.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao
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
}