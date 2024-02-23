package com.comunidadedevspace.taskbeats.presentation.events

import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem

sealed class TaskListEvents {
    data class OnItemClick(val task: TaskItem) : TaskListEvents()
    data class OnFavoriteButtonClick(val task: TaskItem) : TaskListEvents()
}