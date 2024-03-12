package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteDialogUseCase {
    suspend operator fun invoke(task: TaskItem?): Flow<UiEvent> {
        return flow {
            if (task != null) {
                emit(
                    UiEvent.ShowDialog(
                        title = "Delete task",
                        message = "Are you sure you want to delete this task?",
                        positiveText = "Yes",
                        negativeText = "No",
                        task = task
                    )
                )
            } else {
                emit(
                    UiEvent.ShowSnackBar(
                        message = "There is no item to delete!",
                        action = null
                    )
                )
            }
        }
    }
}