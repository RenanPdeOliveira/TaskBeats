package com.comunidadedevspace.taskbeats.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItem)

    @Delete
    suspend fun delete(task: TaskItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: TaskItem)

    @Query("SELECT * FROM TaskItem")
    fun getAll(): LiveData<List<TaskItem>>

    /*@Query("SELECT * FROM TaskItem WHERE isFavorite = true")
    fun getAllFavorite(): LiveData<List<TaskItem>>*/
}