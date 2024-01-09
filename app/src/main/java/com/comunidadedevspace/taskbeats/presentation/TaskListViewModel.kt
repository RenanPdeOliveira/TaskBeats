package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<TaskItem>> = taskDao.getAll()

    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }

}