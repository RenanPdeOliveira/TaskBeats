package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.domain.TaskRepository
import com.comunidadedevspace.taskbeats.presentation.events.DetailEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListDetailViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun actionCRUD(event: DetailEvents) {

        when (event) {
            is DetailEvents.OnAddItemClick -> {
                addItem(event.task)
            }

            is DetailEvents.OnEditItemClick -> {
                updateItem(event.task)
            }

            is DetailEvents.OnDeleteItemClick -> {
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
        if (task.title.isNotBlank() && task.desc.isNotBlank()) {
            repository.insert(task)
            _uiEvent.send(UiEvent.Navigate("main_screen"))
            _uiEvent.send(UiEvent.ShowSnackBar(message = "You added a new task ${task.title}"))
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in all fields", action = "Close"))
        }
    }

    private fun updateItem(task: TaskItem) = viewModelScope.launch {
        if (task.title.isNotBlank() && task.desc.isNotBlank()) {
            repository.update(task)
            _uiEvent.send(UiEvent.Navigate("main_screen"))
            _uiEvent.send(UiEvent.ShowSnackBar(message = "You updated the task ${task.title}"))
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar(message = "Fill in all fields", action = "Close"))
        }
    }

    private fun deleteItem(task: TaskItem?) = viewModelScope.launch {
        if (task != null) {
            repository.delete(task)
            _uiEvent.send(UiEvent.Navigate("main_screen"))
            _uiEvent.send(UiEvent.ShowSnackBar(message = "You deleted the task ${task.title}"))
        } else {
            _uiEvent.send(
                UiEvent.ShowSnackBar(
                    message = "There is no item to delete!",
                    action = "Close"
                )
            )
        }
    }

    companion object {
        fun getFactoryViewModel(application: Application): ViewModelProvider.Factory {
            val dao = (application as TaskBeatsApplication).getRepository()
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskListDetailViewModel(dao) as T
                }
            }
        }
    }

}