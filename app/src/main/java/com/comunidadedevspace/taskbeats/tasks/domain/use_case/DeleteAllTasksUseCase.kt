package com.comunidadedevspace.taskbeats.tasks.domain.use_case

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiText
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
                    message = UiText.StringResource(R.string.snackbar_delete_all_message),
                    action = UiText.StringResource(R.string.snackbar_action_close)
                )
            )
        }
    }
}