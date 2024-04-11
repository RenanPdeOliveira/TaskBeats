package com.comunidadedevspace.taskbeats.tasks.presentation.task_list_view_pager

sealed class TaskListViewPagerEvent {
    object OnDeleteAllButtonClick : TaskListViewPagerEvent()
    object OnYesDialogButtonClick : TaskListViewPagerEvent()
}