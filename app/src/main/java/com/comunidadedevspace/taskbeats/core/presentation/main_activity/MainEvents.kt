package com.comunidadedevspace.taskbeats.core.presentation.main_activity

sealed class MainEvents {
    object OnTaskListNavigationClick : MainEvents()
    object OnNewsListNavigationClick : MainEvents()
    object OnAddTaskClick : MainEvents()
}