package com.comunidadedevspace.taskbeats.tasks.presentation.task_list

import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

sealed class TaskListEvents {
    data class OnItemClick(val task: TaskItem) : TaskListEvents()
    data class OnFavoriteButtonClick(val task: TaskItem) : TaskListEvents()
    data class OnDoneButtonClick(val task: TaskItem) : TaskListEvents()
    data class OnDeleteButtonClick(val task: TaskItem) : TaskListEvents()
}