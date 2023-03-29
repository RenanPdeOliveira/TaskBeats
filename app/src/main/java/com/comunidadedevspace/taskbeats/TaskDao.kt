package com.comunidadedevspace.taskbeats

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Adicione um item na tabela
    fun insert(task: TaskItem)

    @Delete // Delete um item da tabela
    fun delete(task: TaskItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: TaskItem)

    @Query("Select * from TaskItem") // Selecione todos da tabela taskItem
    fun getAll(): List<TaskItem>
}