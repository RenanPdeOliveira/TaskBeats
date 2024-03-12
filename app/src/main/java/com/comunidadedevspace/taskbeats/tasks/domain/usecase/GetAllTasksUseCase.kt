package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow

class GetAllTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(): LiveData<List<TaskItem>> {
        return repository.getAll()
    }
}