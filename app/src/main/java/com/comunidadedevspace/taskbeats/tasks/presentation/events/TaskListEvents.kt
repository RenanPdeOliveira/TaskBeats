package com.comunidadedevspace.taskbeats.tasks.presentation.events

import com.comunidadedevspace.taskbeats.tasks.data.TaskItem

sealed class TaskListEvents {
    data class OnItemClick(val task: TaskItem) : TaskListEvents()
    data class OnFavoriteButtonClick(val task: TaskItem) : TaskListEvents()
    data class OnDoneButtonClick(val task: TaskItem) : TaskListEvents()
    data class OnDeleteButtonClick(val task: TaskItem) : TaskListEvents()
}