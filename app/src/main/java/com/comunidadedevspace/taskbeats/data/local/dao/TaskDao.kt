package com.comunidadedevspace.taskbeats.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItem)

    @Delete
    suspend fun delete(task: TaskItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: TaskItem)

    @Query("DELETE FROM TaskItem")
    suspend fun deleteAll()

    @Query("SELECT * FROM TaskItem")
    fun getAll(): LiveData<List<TaskItem>>
}