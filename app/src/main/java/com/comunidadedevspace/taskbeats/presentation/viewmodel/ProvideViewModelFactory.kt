package com.comunidadedevspace.taskbeats.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import java.lang.IllegalArgumentException

class ProvideViewModelFactory(
    private val app: Application
): ViewModelProvider.Factory {
    private val repository = (app as TaskBeatsApplication).getRepository()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TaskListDetailViewModel::class.java -> TaskListDetailViewModel(repository) as T
            TaskListViewModel::class.java -> TaskListViewModel(repository) as T
            TaskListViewPagerViewModel::class.java -> TaskListViewPagerViewModel(repository) as T
            else -> IllegalArgumentException("Unknown viewModel instance $modelClass") as T
        }
    }
}