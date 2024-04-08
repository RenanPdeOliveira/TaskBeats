package com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: TaskRepository,
    private val context: Context
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
        _uiEvent.send(UiEvent.ShowSnackBar(message = context.getString(R.string.snackbar_delete_message) + " ${task.title}"))
    }
}