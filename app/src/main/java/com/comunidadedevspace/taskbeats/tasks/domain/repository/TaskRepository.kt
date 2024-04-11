package com.comunidadedevspace.taskbeats.tasks.domain.repository

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

interface TaskRepository {

    suspend fun insert(task: TaskItem)

    suspend fun delete(task: TaskItem)

    suspend fun update(task: TaskItem)

    suspend fun deleteAll()

    fun getAll(): LiveData<List<TaskItem>>
}