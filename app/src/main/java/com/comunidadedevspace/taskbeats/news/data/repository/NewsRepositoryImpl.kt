package com.comunidadedevspace.taskbeats.news.data.repository

import com.comunidadedevspace.taskbeats.core.domain.util.DataError
import com.comunidadedevspace.taskbeats.core.domain.util.Result
import com.comunidadedevspace.taskbeats.news.data.local.NewsDao
import com.comunidadedevspace.taskbeats.news.data.mappers.toNewsDomain
import com.comunidadedevspace.taskbeats.news.data.mappers.toNewsItem
import com.comunidadedevspace.taskbeats.news.data.remote.NewsService
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val api: NewsService
): NewsRepository {
    override suspend fun fetchTopNews(): Result<List<NewsDomain>, DataError.Network> {
        return try {
            Result.Success(data = api.fetchTopNews().data.toNewsDomain())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Result.Error(error = DataError.Network.TOP_NEWS_NOTY_FOUND_EXCEPTION)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(error = DataError.Network.TOP_NEWS_NOTY_FOUND_HTTP_EXCEPTION)
        }
    }

    override suspend fun fetchAllNews(): Result<List<NewsDomain>, DataError.Network> {
        return try {
            Result.Success(data = api.fetchAllNews().data.toNewsDomain())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Result.Error(error = DataError.Network.ALL_NEWS_NOTY_FOUND_EXCEPTION)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(error = DataError.Network.ALL_NEWS_NOTY_FOUND_HTTP_EXCEPTION)
        }
    }

    override suspend fun insertNews(news: NewsDomain) {
        newsDao.insertNews(news.toNewsItem())
    }

    override suspend fun deleteNewsById(id: String) {
        newsDao.deleteNewsById(id)
    }

    override fun getAllNews(): Flow<List<NewsDomain>> {
        return newsDao.getAllNews().toNewsDomain()
    }
}