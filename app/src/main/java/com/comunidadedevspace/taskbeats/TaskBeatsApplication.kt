package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.core.data.local.TaskDataBase
import com.comunidadedevspace.taskbeats.news.data.remote.RetrofitModule
import com.comunidadedevspace.taskbeats.core.data.repository.TaskRepositoryImpl
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository

class TaskBeatsApplication : Application() {

    private lateinit var db: TaskDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext, TaskDataBase::class.java, "Data_Base_Task"
        ).fallbackToDestructiveMigration().build()
    }

    fun getRepository(): TaskRepository {
        return TaskRepositoryImpl(db.taskDao(), db.newsDao(), RetrofitModule.createNewsService())
    }

}