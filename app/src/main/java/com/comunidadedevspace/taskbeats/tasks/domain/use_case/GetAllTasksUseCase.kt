package com.comunidadedevspace.taskbeats.tasks.domain.use_case

import androidx.lifecycle.LiveData
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

class GetAllTasksUseCase(
    private val repository: TaskRepository
) {
    operator fun invoke(): LiveData<List<TaskItem>> {
        return repository.getAll()
    }
}