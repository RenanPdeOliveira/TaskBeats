package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: TaskItem): Flow<UiEvent> {
        return flow {
            if (task.title.isNotBlank() && task.desc.isNotBlank()) {
                repository.insert(task)
                emit(UiEvent.Navigate("main_screen"))
                emit(UiEvent.ShowSnackBar(
                    message = "You added a new task ${task.title}",
                    action = "Close"
                ))
            } else {
                emit(UiEvent.ShowSnackBar(message = "Fill in all fields", action = "Close"))
            }
        }
    }
}