package com.comunidadedevspace.taskbeats.core.presentation.view_model_factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.comunidadedevspace.taskbeats.news.presentation.news_list.NewsListViewModel
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.news.domain.use_case.DeleteNewsByIdUseCase
import com.comunidadedevspace.taskbeats.news.domain.use_case.GetAllFavoriteNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.use_case.GetAllNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.use_case.GetTopNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.use_case.InsertNewsUseCase
import com.comunidadedevspace.taskbeats.news.domain.use_case.NewsListUseCase
import com.comunidadedevspace.taskbeats.tasks.data.use_case.DeleteAllDialogUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.data.use_case.DeleteAllTasksUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.data.use_case.DeleteDialogUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.data.use_case.DeleteTaskUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.GetAllTasksUseCase
import com.comunidadedevspace.taskbeats.tasks.data.use_case.InsertTaskUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.TaskListUseCase
import com.comunidadedevspace.taskbeats.tasks.data.use_case.UpdateTaskUseCaseImpl
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list_detail.TaskListDetailViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list.TaskListViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list_view_pager.TaskListViewPagerViewModel
import java.lang.IllegalArgumentException

class ProvideViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {
    private val taskRepository = (app as TaskBeatsApplication).getTaskRepository()
    private val newsRepository = (app as TaskBeatsApplication).getNewsRepository()
    private val taskListUseCase = TaskListUseCase(
        insertTaskUseCase = InsertTaskUseCaseImpl(taskRepository),
        updateTaskUseCase = UpdateTaskUseCaseImpl(taskRepository),
        deleteTaskUseCase = DeleteTaskUseCaseImpl(taskRepository),
        deleteDialogUseCase = DeleteDialogUseCaseImpl(),
        deleteAllTasksUseCase = DeleteAllTasksUseCaseImpl(taskRepository),
        deleteAllDialogUseCase = DeleteAllDialogUseCaseImpl(),
        getAllTasksUseCase = GetAllTasksUseCase(taskRepository)
    )
    private val newsListUseCase = NewsListUseCase(
        getAllNewsUseCase = GetAllNewsUseCase(newsRepository),
        getTopNewsUseCase = GetTopNewsUseCase(newsRepository),
        insertNewsUseCase = InsertNewsUseCase(newsRepository),
        deleteNewsByIdUseCase = DeleteNewsByIdUseCase(newsRepository),
        getAllFavoriteNewsUseCase = GetAllFavoriteNewsUseCase(newsRepository)
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TaskListDetailViewModel::class.java -> TaskListDetailViewModel(taskListUseCase) as T
            TaskListViewModel::class.java -> TaskListViewModel(taskRepository) as T
            TaskListViewPagerViewModel::class.java -> TaskListViewPagerViewModel(taskListUseCase) as T
            NewsListViewModel::class.java -> NewsListViewModel(newsListUseCase) as T
            else -> IllegalArgumentException("Unknown viewModel instance $modelClass") as T
        }
    }
}