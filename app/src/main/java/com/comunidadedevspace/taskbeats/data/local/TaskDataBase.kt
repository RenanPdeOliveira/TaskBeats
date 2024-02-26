package com.comunidadedevspace.taskbeats.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comunidadedevspace.taskbeats.data.local.dao.NewsDao
import com.comunidadedevspace.taskbeats.data.local.dao.TaskDao
import com.comunidadedevspace.taskbeats.data.local.entity.NewsItem
import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem

@Database(entities = [TaskItem::class, NewsItem::class], version = 6)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun newsDao(): NewsDao
}