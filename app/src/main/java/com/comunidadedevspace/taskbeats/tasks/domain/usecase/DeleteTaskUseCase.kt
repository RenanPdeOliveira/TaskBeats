package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: TaskItem): Flow<UiEvent> {
        return flow {
            repository.delete(task)
            emit(UiEvent.Navigate("main_screen"))
            emit(
                UiEvent.ShowSnackBar(
                    message = "You deleted the task ${task.title}",
                    action = "Close"
                )
            )
        }
    }
}