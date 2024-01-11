package com.comunidadedevspace.taskbeats.domain

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.data.local.TaskItem

interface TaskRepository {

    suspend fun insert(task: TaskItem)

    suspend fun delete(task: TaskItem)

    suspend fun update(task: TaskItem)

    fun getAll(): LiveData<List<TaskItem>>
}