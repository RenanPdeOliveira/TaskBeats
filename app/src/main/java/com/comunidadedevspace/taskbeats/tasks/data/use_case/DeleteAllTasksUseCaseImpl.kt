package com.comunidadedevspace.taskbeats.tasks.data.use_case

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.tasks.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.DeleteAllTasksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAllTasksUseCaseImpl(
    private val repository: TaskRepository
): DeleteAllTasksUseCase {
    override suspend operator fun invoke(): Flow<UiEvent> {
        return flow {
            repository.deleteAll()
            emit(
                UiEvent.ShowSnackBar(
                    message = UiText.StringResource(R.string.snack_bar_delete_all_message),
                    action = UiText.StringResource(R.string.snack_bar_action_close)
                )
            )
        }
    }
}