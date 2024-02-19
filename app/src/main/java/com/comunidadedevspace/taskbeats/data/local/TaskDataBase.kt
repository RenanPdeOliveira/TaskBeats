package com.comunidadedevspace.taskbeats.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class, NewsItem::class], version = 5)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun newsDao(): NewsDao
}