package com.comunidadedevspace.taskbeats.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}