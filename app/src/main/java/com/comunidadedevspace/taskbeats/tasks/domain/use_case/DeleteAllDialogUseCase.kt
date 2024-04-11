package com.comunidadedevspace.taskbeats.tasks.domain.use_case

import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow

interface DeleteAllDialogUseCase {
    suspend operator fun invoke(list: List<TaskItem>): Flow<UiEvent>
}