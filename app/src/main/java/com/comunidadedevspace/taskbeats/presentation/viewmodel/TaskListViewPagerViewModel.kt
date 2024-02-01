package com.comunidadedevspace.taskbeats.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.domain.TaskRepository
import com.comunidadedevspace.taskbeats.presentation.events.TaskListViewPagerEvent
import com.comunidadedevspace.taskbeats.util.UiEvent
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
                    message = "Are you sure you want to delete all tasks?"
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

    companion object {
        fun getFactory(app: Application): ViewModelProvider.Factory {
            val repository = (app as TaskBeatsApplication).getRepository()
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskListViewPagerViewModel(repository) as T
                }
            }
        }
    }
}