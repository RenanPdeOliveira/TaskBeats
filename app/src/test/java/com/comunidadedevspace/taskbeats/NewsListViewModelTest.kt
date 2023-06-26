package com.comunidadedevspace.taskbeats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.presentation.NewsListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val newsService: NewsService = mock()

    private lateinit var underTest: NewsListViewModel

    @Test
    fun `LiveData Test`() {
        runBlocking {
            // GIVEN
            val topNews = listOf<NewsDto>(
                NewsDto(
                    id = "1",
                    title = "Title 1",
                    content = "Content 1",
                    imageUrl = "Image 1"
                ),
                NewsDto(
                    id = "2",
                    title = "Title 2",
                    content = "Content 2",
                    imageUrl = "Image 2"
                )
            )
            val allNews = listOf<NewsDto>(
                NewsDto(
                    id = "3",
                    title = "Title 3",
                    content = "Content 3",
                    imageUrl = "Image 3"
                ),
                NewsDto(
                    id = "4",
                    title = "Title 4",
                    content = "Content 4",
                    imageUrl = "Image 4"
                )
            )

            val topResponse = NewsResponse(data = topNews)
            val allResponse = NewsResponse(data = allNews)
            whenever(newsService.fetchTopNews()).thenReturn(topResponse)
            whenever(newsService.fetchAllNews()).thenReturn(allResponse)

            // WHEN
            underTest = NewsListViewModel(newsService)
            val result = underTest.newsLiveData.getOrAwaitValue()

            // THEN
            assert(result == topNews + allNews)
        }
    }
}