package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import android.content.Context
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateTaskUseCaseImpl(
    private val repository: TaskRepository
): UpdateTaskUseCase {
    override suspend operator fun invoke(task: TaskItem, context: Context): Flow<UiEvent> {
        return flow {
            if (task.title.isBlank() || task.desc.isBlank()) {
                emit(UiEvent.ShowSnackBar(
                    message = context.getString(R.string.snackbar_fill_fields_message),
                    action = context.getString(R.string.snackbar_action_close)
                ))
                return@flow
            }
            repository.update(task)
            emit(UiEvent.Navigate("main_screen"))
            emit(
                UiEvent.ShowSnackBar(
                    message = context.getString(R.string.snackbar_update_message) + " ${task.title}",
                    action = context.getString(R.string.snackbar_action_close)
                )
            )
        }
    }
}