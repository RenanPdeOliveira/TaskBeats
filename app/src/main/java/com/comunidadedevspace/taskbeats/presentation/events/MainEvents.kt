package com.comunidadedevspace.taskbeats.presentation.events

sealed class MainEvents {
    object OnTaskListNavigationClick : MainEvents()
    object OnNewsListNavigationClick : MainEvents()
    object OnAddTaskClick : MainEvents()
}