package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.TaskDao
import com.comunidadedevspace.taskbeats.data.TaskItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<TaskItem>> = taskDao.getAll()

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
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    private fun updateItem(task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.update(task)
        }
    }

    private fun deleteItem(task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
        }
    }

    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }

}