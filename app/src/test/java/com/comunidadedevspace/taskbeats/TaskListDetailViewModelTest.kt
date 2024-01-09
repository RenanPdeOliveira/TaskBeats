package com.comunidadedevspace.taskbeats

import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.presentation.TaskListDetailViewModel
import com.comunidadedevspace.taskbeats.presentation.events.DetailEvents
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

    private val taskDao: TaskDao = mock()

    private val underTest: TaskListDetailViewModel by lazy {
        TaskListDetailViewModel(taskDao)
    }

    @Test
    fun insert() = runTest {
        // Given
        val task = TaskItem(
            1,
            "Buy",
            "Banana",
            true
        )

        // When
        underTest.actionCRUD(DetailEvents.OnAddItemClick(task))

        // Then
        verify(taskDao).insert(task)
    }

    @Test
    fun update() = runTest {
        // Given
        val task = TaskItem(
            2,
            "Study",
            "Math",
            false
        )

        // When
        underTest.actionCRUD(DetailEvents.OnEditItemClick(task))

        // Then
        verify(taskDao).update(task)
    }

    @Test
    fun delete() = runTest {
        // Given
        val task = TaskItem(
            3,
            "Work",
            "Create an app",
            true
        )

        // When
        underTest.actionCRUD(DetailEvents.OnDeleteItemClick(task))

        // Then
        verify(taskDao).delete(task)
    }

}