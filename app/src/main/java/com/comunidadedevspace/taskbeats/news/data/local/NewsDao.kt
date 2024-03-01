package com.comunidadedevspace.taskbeats.news.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsItem)

    @Query("DELETE FROM NewsItem WHERE id = :id")
    suspend fun deleteNewsById(id: String)

    @Query("SELECT * FROM NewsItem")
    fun getAllNews(): Flow<List<NewsItem>>
}