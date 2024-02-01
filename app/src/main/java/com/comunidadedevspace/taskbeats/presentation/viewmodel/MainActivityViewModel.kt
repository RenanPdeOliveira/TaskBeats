package com.comunidadedevspace.taskbeats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.presentation.events.MainEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: MainEvents) {
        when (event) {
            is MainEvents.OnTaskListNavigationClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("task_list_screen"))
                }
            }

            is MainEvents.OnNewsListNavigationClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("news_list_screen"))
                }
            }

            is MainEvents.OnAddTaskClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("detail_screen"))
                }
            }
        }
    }
}