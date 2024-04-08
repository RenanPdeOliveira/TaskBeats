package com.comunidadedevspace.taskbeats.tasks.domain.usecase

import android.content.Context
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import kotlinx.coroutines.flow.Flow

interface DeleteDialogUseCase {
    suspend operator fun invoke(task: TaskItem?, context: Context): Flow<UiEvent>
}