package com.comunidadedevspace.taskbeats.tasks.data.use_case

import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.DeleteAllDialogUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteAllDialogUseCaseImpl: DeleteAllDialogUseCase {
    override suspend operator fun invoke(list: List<TaskItem>): Flow<UiEvent> {
        return flow {
            if (list.isEmpty()) {
                emit(
                    UiEvent.ShowSnackBar(
                        message = UiText.StringResource(R.string.snackbar_no_item),
                        action = UiText.StringResource(R.string.snackbar_action_close)
                    )
                )
            } else {
                emit(
                    UiEvent.ShowDialog(
                        title = UiText.StringResource(R.string.dialog_title_delete_all),
                        message = UiText.StringResource(R.string.dialog_title_message_all),
                        positiveText = UiText.StringResource(R.string.dialog_title_positive),
                        negativeText = UiText.StringResource(R.string.dialog_title_negative)
                    )
                )
            }
        }
    }
}