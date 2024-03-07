package com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.tasks.data.TaskItem
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.presentation.events.TaskListViewPagerEvent
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListViewPagerViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val listLiveData = repository.getAll()
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
        if (list.isEmpty()) {
            _uiEvent.send(
                UiEvent.ShowSnackBar(
                    message = "There is no item to delete!",
                    action = "Close"
                )
            )
        } else {
            _uiEvent.send(
                UiEvent.ShowDialog(
                    title = "Delete all",
                    message = "Are you sure you want to delete all tasks?",
                    positiveText = "Yes",
                    negativeText = "No"
                )
            )
        }
    }

    private fun deleteAllItems() = viewModelScope.launch {
        repository.deleteAll()
        _uiEvent.send(
            UiEvent.ShowSnackBar(
                message = "You have deleted all items!",
                action = "Close"
            )
        )
    }

}