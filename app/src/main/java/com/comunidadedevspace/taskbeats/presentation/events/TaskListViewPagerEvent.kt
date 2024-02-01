package com.comunidadedevspace.taskbeats.presentation.events

sealed class TaskListViewPagerEvent {
    object OnDeleteAllButtonClick : TaskListViewPagerEvent()
    object OnYesDialogButtonClick : TaskListViewPagerEvent()
}