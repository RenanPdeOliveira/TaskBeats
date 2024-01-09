package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.presentation.events.DetailEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListDetailViewModel(
    private val taskDao: TaskDao
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
            taskDao.insert(task)
            _uiEvent.send(UiEvent.Navigate("main_screen"))
            _uiEvent.send(UiEvent.ShowSnackBar("You added a new task ${task.title}"))
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar("Fill in all fields"))
        }
    }

    private fun updateItem(task: TaskItem) = viewModelScope.launch {
        if (task.title.isNotBlank() && task.desc.isNotBlank()) {
            taskDao.update(task)
            _uiEvent.send(UiEvent.Navigate("main_screen"))
            _uiEvent.send(UiEvent.ShowSnackBar("You updated the task ${task.title}"))
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar("Fill in all fields"))
        }
    }

    private fun deleteItem(task: TaskItem?) = viewModelScope.launch {
        if (task != null) {
            taskDao.delete(task)
            _uiEvent.send(UiEvent.Navigate("main_screen"))
            _uiEvent.send(UiEvent.ShowSnackBar("You deleted the task ${task.title}"))
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar("There is no item to delete!"))
        }
    }

    companion object {
        fun getFactoryViewModel(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance = (application as TaskBeatsApplication).getDataBase()
            val dao = dataBaseInstance.taskDao()
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskListDetailViewModel(dao) as T
                }
            }
        }
    }

}