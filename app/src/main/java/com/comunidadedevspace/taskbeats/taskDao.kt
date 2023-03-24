package com.comunidadedevspace.taskbeats

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface taskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Adicione um item na tabela
    fun insert(task: taskItem)

    @Delete()
    fun delete(task: taskItem)

    @Query("Select * from taskItem") // Leia todos os itens da tabela
    fun getAll(): List<taskItem>
}