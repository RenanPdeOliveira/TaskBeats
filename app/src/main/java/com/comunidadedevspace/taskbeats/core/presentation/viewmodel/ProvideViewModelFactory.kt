package com.comunidadedevspace.taskbeats.core.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.comunidadedevspace.taskbeats.news.presentation.viewmodel.NewsListViewModel
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.news.domain.usecase.DeleteNewsByIdUseCase
import com.comunidadedevspace.taskbeats.news.domain.usecase.GetAllFavoriteNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.usecase.GetAllNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.usecase.GetTopNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.usecase.InsertNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.usecase.NewsListUseCase
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.DeleteAllDialogUseCase
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.DeleteAllTasksUseCase
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.DeleteDialogUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.DeleteTaskUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.GetAllTasksUseCase
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.InsertTaskUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.TaskListUseCase
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.UpdateTaskUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListDetailViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListViewPagerViewModel
import java.lang.IllegalArgumentException

class ProvideViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {
    private val repository = (app as TaskBeatsApplication).getRepository()
    private val taskListDetailUseCase = TaskListUseCase(
        insertTaskUseCase = InsertTaskUseCaseImpl(repository),
        updateTaskUseCase = UpdateTaskUseCaseImpl(repository),
        deleteTaskUseCase = DeleteTaskUseCaseImpl(repository),
        deleteDialogUseCase = DeleteDialogUseCaseImpl(),
        deleteAllTasksUseCase = DeleteAllTasksUseCase(repository),
        deleteAllDialogUseCase = DeleteAllDialogUseCase(),
        getAllTasksUseCase = GetAllTasksUseCase(repository)
    )
    private val newsListUseCase = NewsListUseCase(
        getAllNewsUseCase = GetAllNewsUseCase(repository),
        getTopNewsUseCase = GetTopNewsUseCase(repository),
        insertNewsUseCase = InsertNewsUseCase(repository),
        deleteNewsByIdUseCase = DeleteNewsByIdUseCase(repository),
        getAllFavoriteNewsUseCase = GetAllFavoriteNewsUseCase(repository)
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TaskListDetailViewModel::class.java -> TaskListDetailViewModel(taskListDetailUseCase) as T
            TaskListViewModel::class.java -> TaskListViewModel(repository) as T
            TaskListViewPagerViewModel::class.java -> TaskListViewPagerViewModel(taskListDetailUseCase) as T
            NewsListViewModel::class.java -> NewsListViewModel(newsListUseCase) as T
            else -> IllegalArgumentException("Unknown viewModel instance $modelClass") as T
        }
    }
}