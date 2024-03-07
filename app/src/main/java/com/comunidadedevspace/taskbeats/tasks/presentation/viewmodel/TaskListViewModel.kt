package com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.tasks.data.TaskItem
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val taskList: LiveData<List<TaskItem>> = repository.getAll()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskListEvents) {
        when (event) {
            is TaskListEvents.OnItemClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("detail_screen"))
                }
            }

            is TaskListEvents.OnFavoriteButtonClick -> {
                updateTask(event.task)
            }

            is TaskListEvents.OnDoneButtonClick -> {
                updateTask(event.task)
            }

            is TaskListEvents.OnDeleteButtonClick -> {
                deleteTask(event.task)
            }
        }
    }

    private fun updateTask(task: TaskItem) = viewModelScope.launch {
        repository.update(task)
    }

    private fun deleteTask(task: TaskItem) = viewModelScope.launch {
        repository.delete(task)
        _uiEvent.send(UiEvent.ShowSnackBar(message = "You have deleted ${task.title}"))
    }

}