package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import android.content.Context
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteDialogUseCaseImpl : DeleteDialogUseCase {
    override suspend operator fun invoke(task: TaskItem?, context: Context): Flow<UiEvent> {
        return flow {
            if (task == null) {
                emit(
                    UiEvent.ShowSnackBar(
                        message = context.getString(R.string.snackbar_no_item),
                        action = null
                    )
                )
                return@flow
            }
            emit(
                UiEvent.ShowDialog(
                    title = context.getString(R.string.dialog_title_delete),
                    message = context.getString(R.string.dialog_title_delete) + " ${task.title}",
                    positiveText = context.getString(R.string.dialog_title_positive),
                    negativeText = context.getString(R.string.dialog_title_negative),
                    task = task
                )
            )
        }
    }
}