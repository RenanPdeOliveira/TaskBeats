package com.comunidadedevspace.taskbeats.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 2)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}