package com.comunidadedevspace.taskbeats

import com.comunidadedevspace.taskbeats.data.TaskDao
import com.comunidadedevspace.taskbeats.data.TaskItem
import com.comunidadedevspace.taskbeats.presentation.ActionType
import com.comunidadedevspace.taskbeats.presentation.TaskAction
import com.comunidadedevspace.taskbeats.presentation.TaskListViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TaskListViewModelTest {

    private val taskDao: TaskDao = mock()

    private val underTest: TaskListViewModel by lazy {
        TaskListViewModel(taskDao)
    }

    @Test
    fun delete_Task() = runTest {
        // Given
        val taskAction = TaskAction(
            task = TaskItem(
                id = 1,
                title = "Compra",
                desc = "Cerveja"
            ),
            actionType = ActionType.DELETE.name
        )

        // When
        underTest.actionCRUD(taskAction)

        // Then
        verify(taskDao).delete(taskAction.task)
    }

    @Test
    fun update_task() = runTest {
        // Given
        val taskAction = TaskAction(
            task = TaskItem(
                id = 2,
                title = "Lavar",
                desc = "Carro"
            ),
            actionType = ActionType.UPDATE.name
        )

        // When
        underTest.actionCRUD(taskAction)

        // Then
        verify(taskDao).update(taskAction.task)
    }

    @Test
    fun insert_Task() = runTest {
        // Given
        val taskAction = TaskAction(
            task = TaskItem(
                id = 3,
                title = "Sabado",
                desc = "Festa"
            ),
            actionType = ActionType.CREATE.name
        )

        // When
        underTest.actionCRUD(taskAction)

        // Then
        verify(taskDao).insert(taskAction.task)
    }

}