package com.comunidadedevspace.taskbeats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val taskDao: TaskDao
) : ViewModel() {

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

            is TaskListEvents.OnAddButtonClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("detail_screen"))
                }
            }
        }
    }

    private fun changeFavoriteButton(task: TaskItem) = viewModelScope.launch {
        taskDao.update(task)
    }
}