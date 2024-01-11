package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.data.local.TaskDataBase
import com.comunidadedevspace.taskbeats.data.repository.TaskRepositoryImpl
import com.comunidadedevspace.taskbeats.domain.TaskRepository

class TaskBeatsApplication : Application() {

    private lateinit var db: TaskDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext, TaskDataBase::class.java, "Data_Base_Task"
        ).fallbackToDestructiveMigration().build()
    }

    fun getRepository(): TaskRepository {
        return TaskRepositoryImpl(db.taskDao())
    }

}