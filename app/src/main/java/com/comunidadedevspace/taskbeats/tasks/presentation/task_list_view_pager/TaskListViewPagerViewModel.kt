package com.comunidadedevspace.taskbeats.tasks.presentation.task_list_view_pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.TaskListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListViewPagerViewModel(
    private val taskListUseCase: TaskListUseCase
) : ViewModel() {

    private val listLiveData = taskListUseCase.getAllTasksUseCase()
    private var list = listOf<TaskItem>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            listLiveData.asFlow().collect {
                list = it
            }
        }
    }

    fun onEvent(event: TaskListViewPagerEvent) {
        when (event) {
            is TaskListViewPagerEvent.OnDeleteAllButtonClick -> {
                showDialog()
            }

            is TaskListViewPagerEvent.OnYesDialogButtonClick -> {
                deleteAllItems()
            }
        }

    }

    private fun showDialog() = viewModelScope.launch {
        taskListUseCase.deleteAllDialogUseCase(list).collect { sendUiEvent ->
            _uiEvent.send(sendUiEvent)
        }
    }

    private fun deleteAllItems() = viewModelScope.launch {
        taskListUseCase.deleteAllTasksUseCase().collect { sendUiEvent ->
            _uiEvent.send(sendUiEvent)
        }
    }
}