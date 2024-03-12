package com.comunidadedevspace.taskbeats.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comunidadedevspace.taskbeats.news.data.local.NewsDao
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskDao
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem

@Database(entities = [TaskItem::class, NewsItem::class], version = 6)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun newsDao(): NewsDao
}