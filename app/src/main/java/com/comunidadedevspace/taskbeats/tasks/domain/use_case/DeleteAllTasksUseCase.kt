package com.comunidadedevspace.taskbeats.tasks.domain.use_case

import com.comunidadedevspace.taskbeats.core.util.UiEvent
import kotlinx.coroutines.flow.Flow

interface DeleteAllTasksUseCase {
    suspend operator fun invoke(): Flow<UiEvent>
}