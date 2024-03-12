package com.comunidadedevspace.taskbeats

import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListDetailViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.events.DetailEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: TaskRepository = mock()

    private val underTest: TaskListDetailViewModel by lazy {
        TaskListDetailViewModel(repository)
    }

    @Test
    fun insert() = runTest {
        // Given
        val task = TaskItem(
            1,
            "Buy",
            "Banana",
            false,
            R.drawable.baseline_outline_grade_24
        )

        // When
        underTest.onEvent(DetailEvents.OnAddItemClick(task))

        // Then
        verify(repository).insert(task)
    }

    @Test
    fun update() = runTest {
        // Given
        val task = TaskItem(
            2,
            "Study",
            "Math",
            false,
            R.drawable.baseline_outline_grade_24
        )

        // When
        underTest.onEvent(DetailEvents.OnEditItemClick(task))

        // Then
        verify(repository).update(task)
    }

    @Test
    fun delete() = runTest {
        // Given
        val task = TaskItem(
            3,
            "Work",
            "Create an app",
            false,
            R.drawable.baseline_outline_grade_24
        )

        // When
        underTest.onEvent(DetailEvents.OnDeleteItemClick(task))

        // Then
        verify(repository).delete(task)
    }

}