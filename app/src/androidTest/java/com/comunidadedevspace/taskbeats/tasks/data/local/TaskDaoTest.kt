package com.comunidadedevspace.taskbeats.tasks.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.comunidadedevspace.taskbeats.core.data.local.TaskDataBase
import com.comunidadedevspace.taskbeats.getOrAwaitValue
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskDao
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataBase: TaskDataBase
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), TaskDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = dataBase.taskDao()
    }

    @After
    fun teardown() {
        dataBase.close()
    }

    @Test
    fun insertTaskItem() = runTest {
        // GIVEN
        val taskItem = TaskItem(
            1, "titulo", "descricao", "01-01-2024", false, false, 1
        )

        // WHEN
        dao.insert(taskItem)

        // THEN
        val allTasks = dao.getAll().getOrAwaitValue()
        assertThat(allTasks).contains(taskItem)
    }

    @Test
    fun deleteTaskItem() = runTest {
        // GIVEN
        val taskItem = TaskItem(
            1, "titulo", "descricao", "01-01-2024", false, false, 1
        )

        // WHEN
        dao.insert(taskItem)
        dao.delete(taskItem)

        // THEN
        val allTasks = dao.getAll().getOrAwaitValue()
        assertThat(allTasks).doesNotContain(taskItem)
    }

    @Test
    fun updateTaskItem() = runTest {
        // GIVEN
        val taskItem = TaskItem(
            1, "titulo", "descricao", "01-01-2024", false, false, 1
        )
        val taskItemUpdated = TaskItem(
            1, "titulo2", "descricao2", "02-02-2024", true, true, 2
        )
        dao.insert(taskItem)
        dao.update(taskItemUpdated)

        val allTasks = dao.getAll().getOrAwaitValue()
        assertThat(allTasks[0].title).isNotEqualTo(taskItemUpdated.title)
    }
}