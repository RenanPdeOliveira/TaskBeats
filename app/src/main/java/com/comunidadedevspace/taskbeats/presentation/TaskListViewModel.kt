package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.domain.TaskRepository
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val taskListLiveData: LiveData<List<TaskItem>> = repository.getAll()

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

            is TaskListEvents.OnDeleteAllButtonClick -> {
                showDialog()
            }

            is TaskListEvents.OnYesDialogButtonClick -> {
                deleteAllItems()
            }
        }
    }

    private fun changeFavoriteButton(task: TaskItem) = viewModelScope.launch {
        repository.update(task)
    }

    private fun showDialog() = viewModelScope.launch {
        if (taskListLiveData.value!!.isNotEmpty()) {
            _uiEvent.send(UiEvent.ShowDialog(title = "Delete all", message = "Are you sure you want to delete all tasks?"))
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar(message = "There is no item to delete!", action = "Close"))
        }
    }

    private fun deleteAllItems() = viewModelScope.launch {
        repository.deleteAll()
        _uiEvent.send(UiEvent.ShowSnackBar(message = "You have deleted all items!", action = "Close"))
    }

    companion object {
        fun create(application: Application): TaskListViewModel {
            val dao = (application as TaskBeatsApplication).getRepository()
            return TaskListViewModel(dao)
        }
    }

}