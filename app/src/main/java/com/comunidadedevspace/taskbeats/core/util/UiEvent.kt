package com.comunidadedevspace.taskbeats.core.util

import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()
    data class ShowDialog(
        val title: String? = null,
        val message: String? = null,
        val positiveText: String? = null,
        val negativeText: String? = null,
        val task: TaskItem? = null
    ) : UiEvent()
}