package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.data.TaskDataBase

class TaskBeatsApplication : Application() {

    private lateinit var db: TaskDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext, TaskDataBase::class.java, "Data_Base_Task"
        ).build()
    }

    fun getDataBase(): TaskDataBase {
        return db
    }

}