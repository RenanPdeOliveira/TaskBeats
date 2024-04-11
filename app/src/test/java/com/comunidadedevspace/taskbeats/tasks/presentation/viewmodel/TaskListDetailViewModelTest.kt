package com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel

import com.comunidadedevspace.taskbeats.MainDispatcherRule
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.tasks.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.domain.use_case.TaskListUseCase
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list_detail.DetailEvents
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list_detail.TaskListDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TaskListDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: TaskRepository

    /*private val underTest: TaskListDetailViewModel by lazy {
        TaskListDetailViewModel(repository)
    }*/

    @Mock
    private lateinit var taskListUseCase: TaskListUseCase

    private lateinit var viewModel: TaskListDetailViewModel

    @Before
    fun setup() {
        viewModel = TaskListDetailViewModel(taskListUseCase)
    }

    @Test
    fun `insert task item`() = runTest {
        // Given
        val task = TaskItem(
            1,
            "Buy",
            "Banana",
            "01-01-2024",
            false,
            false,
            0
        )

        // When
        viewModel.onEvent(DetailEvents.OnAddItemClick(task))

        // Then
        verify(taskListUseCase.insertTaskUseCase).invoke(task)
    }

    @Test
    fun `update task item`() = runTest {
        // Given
        val task = TaskItem(
            2,
            "Study",
            "Math",
            "01-01-2024",
            false,
            false,
            0
        )

        // When
        viewModel.onEvent(DetailEvents.OnEditItemClick(task))

        // Then
        verify(repository).update(task)
    }

    @Test
    fun `delete task item`() = runTest {
        // Given
        val task = TaskItem(
            3,
            "Work",
            "Create an app",
            "01-01-2024",
            false,
            false,
            0
        )

        // When
        viewModel.onEvent(DetailEvents.OnDeleteItemClick(task))

        // Then
        verify(repository).delete(task)
    }

}