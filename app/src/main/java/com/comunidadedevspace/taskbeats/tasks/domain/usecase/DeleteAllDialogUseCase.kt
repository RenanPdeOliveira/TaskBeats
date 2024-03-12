package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAllDialogUseCase {
    suspend operator fun invoke(list: List<TaskItem>): Flow<UiEvent> {
        return flow {
            if (list.isEmpty()) {
                emit(
                    UiEvent.ShowSnackBar(
                        message = "There is no item to delete!",
                        action = "Close"
                    )
                )
            } else {
                emit(
                    UiEvent.ShowDialog(
                        title = "Delete all",
                        message = "Are you sure you want to delete all tasks?",
                        positiveText = "Yes",
                        negativeText = "No"
                    )
                )
            }
        }
    }
}