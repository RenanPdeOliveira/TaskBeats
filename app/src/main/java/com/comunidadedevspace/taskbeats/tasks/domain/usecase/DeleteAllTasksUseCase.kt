package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAllTasksUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(): Flow<UiEvent> {
        return flow {
            repository.deleteAll()
            emit(
                UiEvent.ShowSnackBar(
                    message = "You have deleted all items!",
                    action = "Close"
                )
            )
        }
    }
}