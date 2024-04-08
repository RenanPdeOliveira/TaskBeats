package com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.tasks.presentation.events.DetailEvents
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.domain.usecase.TaskListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListDetailViewModel(
    private val taskListUseCase: TaskListUseCase,
    private val context: Context
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: DetailEvents) {

        when (event) {
            is DetailEvents.OnAddItemClick -> {
                addItem(event.task)
            }

            is DetailEvents.OnEditItemClick -> {
                updateItem(event.task)
            }

            is DetailEvents.OnDeleteItemClick -> {
                showDialog(event.task)
            }

            is DetailEvents.OnYesDialogButtonClick -> {
                deleteItem(event.task)
            }

            is DetailEvents.OnNavigateBack -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("main_screen"))
                }
            }
        }

    }

    private fun addItem(task: TaskItem) = viewModelScope.launch {
        taskListUseCase.insertTaskUseCase(task, context).collect { sendUiEvent ->
            _uiEvent.send(sendUiEvent)
        }
    }

    private fun updateItem(task: TaskItem) = viewModelScope.launch {
        taskListUseCase.updateTaskUseCase(task = task, context = context).collect { sendUiEvent ->
            _uiEvent.send(sendUiEvent)
        }
    }

    private fun showDialog(task: TaskItem?) = viewModelScope.launch {
        taskListUseCase.deleteDialogUseCase(task, context).collect { sendUiEvent ->
            _uiEvent.send(sendUiEvent)
        }
    }

    private fun deleteItem(task: TaskItem) = viewModelScope.launch {
        taskListUseCase.deleteTaskUseCase(task, context).collect { sendUiEvent ->
            _uiEvent.send(sendUiEvent)
        }
    }
}