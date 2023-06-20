package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import kotlinx.coroutines.launch

class TaskListDetailViewModel(private val taskDao: TaskDao) : ViewModel() {

    fun actionCRUD(taskAction: TaskAction) {

        when (taskAction.actionType) {
            ActionType.CREATE.name -> {
                addItem(taskAction.task)
            }
            ActionType.UPDATE.name -> {
                updateItem(taskAction.task)
            }
            ActionType.DELETE.name -> {
                deleteItem(taskAction.task)
            }
        }

    }

    private fun addItem(task: TaskItem) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    private fun updateItem(task: TaskItem) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    private fun deleteItem(task: TaskItem) {
        viewModelScope.launch {
            taskDao.delete(task)
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