package com.comunidadedevspace.taskbeats.tasks.data.use_case

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.tasks.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.UpdateTaskUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateTaskUseCaseImpl(
    private val repository: TaskRepository
): UpdateTaskUseCase {
    override suspend operator fun invoke(task: TaskItem): Flow<UiEvent> {
        return flow {
            if (task.title.isBlank() || task.desc.isBlank()) {
                emit(UiEvent.ShowSnackBar(
                    message = UiText.StringResource(R.string.snack_bar_fill_fields_message),
                    action = UiText.StringResource(R.string.snack_bar_action_close)
                ))
                return@flow
            }
            repository.update(task)
            emit(UiEvent.Navigate("main_screen"))
            emit(
                UiEvent.ShowSnackBar(
                    message = UiText.StringResource(R.string.snack_bar_update_message, task.title),
                    action = UiText.StringResource(R.string.snack_bar_action_close)
                )
            )
        }
    }
}