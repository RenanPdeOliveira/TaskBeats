package com.comunidadedevspace.taskbeats.tasks.domain.use_case

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteTaskUseCaseImpl(
    private val repository: TaskRepository
): DeleteTaskUseCase {
    override suspend operator fun invoke(task: TaskItem): Flow<UiEvent> {
        return flow {
            repository.delete(task)
            emit(UiEvent.Navigate("main_screen"))
            emit(
                UiEvent.ShowSnackBar(
                    message = UiText.StringResource(R.string.snackbar_delete_message, task.title),
                    action = UiText.StringResource(R.string.snackbar_action_close)
                )
            )
        }
    }
}