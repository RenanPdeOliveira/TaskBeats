package com.comunidadedevspace.taskbeats.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem
import com.comunidadedevspace.taskbeats.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
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
                changeFavoriteButton(event.task)
            }

            is TaskListEvents.OnDeleteButtonClick -> {
                deleteTask(event.task)
            }
        }
    }

    private fun changeFavoriteButton(task: TaskItem) = viewModelScope.launch {
        repository.update(task)
    }

    private fun deleteTask(task: TaskItem) = viewModelScope.launch {
        repository.delete(task)
        _uiEvent.send(UiEvent.ShowSnackBar(message = "You have deleted ${task.title}"))
    }

}