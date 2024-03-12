package com.comunidadedevspace.taskbeats.tasks.presentation.events

import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

sealed class DetailEvents {
    data class OnAddItemClick(val task: TaskItem) : DetailEvents()
    data class OnEditItemClick(val task: TaskItem) : DetailEvents()
    data class OnDeleteItemClick(val task: TaskItem?) : DetailEvents()
    data class OnYesDialogButtonClick(val task: TaskItem): DetailEvents()
    object OnNavigateBack : DetailEvents()
}