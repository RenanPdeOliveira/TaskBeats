package com.comunidadedevspace.taskbeats.tasks.presentation.events

sealed class TaskListViewPagerEvent {
    object OnDeleteAllButtonClick : TaskListViewPagerEvent()
    object OnYesDialogButtonClick : TaskListViewPagerEvent()
}