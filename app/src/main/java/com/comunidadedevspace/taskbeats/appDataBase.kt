package com.comunidadedevspace.taskbeats

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [taskItem::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun taskDao(): taskDao
}