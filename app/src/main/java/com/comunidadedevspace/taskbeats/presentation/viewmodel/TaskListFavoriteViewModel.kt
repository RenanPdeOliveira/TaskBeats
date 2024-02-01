package com.comunidadedevspace.taskbeats.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.domain.TaskRepository
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListFavoriteViewModel(
    private val repository: TaskRepository
): ViewModel() {

    val list = repository.getAll()

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
        }
    }

    private fun changeFavoriteButton(task: TaskItem) = viewModelScope.launch {
        repository.update(task)
    }

    companion object {
        fun getFactory(app: Application): ViewModelProvider.Factory {
            val repository = (app as TaskBeatsApplication).getRepository()
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskListFavoriteViewModel(repository) as T
                }
            }
        }
    }
}