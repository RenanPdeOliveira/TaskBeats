package com.comunidadedevspace.taskbeats.tasks.domain.use_case

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteDialogUseCaseImpl : DeleteDialogUseCase {
    override suspend operator fun invoke(task: TaskItem?): Flow<UiEvent> {
        return flow {
            if (task == null) {
                emit(
                    UiEvent.ShowSnackBar(
                        message = UiText.StringResource(R.string.snackbar_no_item),
                        action = null
                    )
                )
                return@flow
            }
            emit(
                UiEvent.ShowDialog(
                    title = UiText.StringResource(R.string.dialog_title_delete),
                    message = UiText.StringResource(R.string.dialog_title_message, task.title),
                    positiveText = UiText.StringResource(R.string.dialog_title_positive),
                    negativeText = UiText.StringResource(R.string.dialog_title_negative),
                    task = task
                )
            )
        }
    }
}