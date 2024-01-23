package com.comunidadedevspace.taskbeats.util

import com.comunidadedevspace.taskbeats.data.local.TaskItem

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()
    data class ShowDialog(val title: String, val message: String, val task: TaskItem?) : UiEvent()
}