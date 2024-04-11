package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.core.data.local.TaskDataBase
import com.comunidadedevspace.taskbeats.news.data.remote.RetrofitModule
import com.comunidadedevspace.taskbeats.news.data.repository.NewsRepositoryImpl
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository
import com.comunidadedevspace.taskbeats.tasks.data.repository.TaskRepositoryImpl
import com.comunidadedevspace.taskbeats.tasks.domain.repository.TaskRepository

class TaskBeatsApplication : Application() {

    private lateinit var db: TaskDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext, TaskDataBase::class.java, "Data_Base_Task"
        ).fallbackToDestructiveMigration().build()
    }

    fun getTaskRepository(): TaskRepository {
        return TaskRepositoryImpl(db.taskDao())
    }

    fun getNewsRepository(): NewsRepository {
        return NewsRepositoryImpl(db.newsDao(), RetrofitModule.createNewsService())
    }
}