package com.comunidadedevspace.taskbeats.presentation.events

import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem

sealed class DetailEvents {
    data class OnAddItemClick(val task: TaskItem) : DetailEvents()
    data class OnEditItemClick(val task: TaskItem) : DetailEvents()
    data class OnDeleteItemClick(val task: TaskItem?) : DetailEvents()
    data class OnYesDialogButtonClick(val task: TaskItem): DetailEvents()
    object OnNavigateBack : DetailEvents()
}