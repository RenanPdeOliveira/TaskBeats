package com.comunidadedevspace.taskbeats.core.util

import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(
        val message: UiText.StringResource,
        val action: UiText.StringResource? = null
    ) : UiEvent()
    data class ShowDialog(
        val title: UiText.StringResource? = null,
        val message: UiText.StringResource? = null,
        val positiveText: UiText.StringResource? = null,
        val negativeText: UiText.StringResource? = null,
        val task: TaskItem? = null
    ) : UiEvent()
}