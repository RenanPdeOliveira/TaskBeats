package com.comunidadedevspace.taskbeats.core.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.comunidadedevspace.taskbeats.news.presentation.viewmodel.NewsListViewModel
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListDetailViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListViewPagerViewModel
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
            NewsListViewModel::class.java -> NewsListViewModel(repository) as T
            else -> IllegalArgumentException("Unknown viewModel instance $modelClass") as T
        }
    }
}