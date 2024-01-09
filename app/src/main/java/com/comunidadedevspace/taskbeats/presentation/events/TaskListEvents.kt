package com.comunidadedevspace.taskbeats.presentation.events

import com.comunidadedevspace.taskbeats.data.local.TaskItem

sealed class TaskListEvents {
    object OnItemClick : TaskListEvents()
    data class OnFavoriteButtonClick(val task: TaskItem) : TaskListEvents()
    object OnAddButtonClick : TaskListEvents()
}