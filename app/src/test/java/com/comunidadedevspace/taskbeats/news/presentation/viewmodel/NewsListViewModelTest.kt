package com.comunidadedevspace.taskbeats.news.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comunidadedevspace.taskbeats.MainDispatcherRule
import com.comunidadedevspace.taskbeats.news.data.mappers.toNewsItem
import com.comunidadedevspace.taskbeats.news.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.news.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.news.data.remote.NewsService
import com.comunidadedevspace.taskbeats.news.domain.usecase.NewsListUseCase
import com.comunidadedevspace.taskbeats.news.presentation.events.NewsListEvents
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NewsListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsListUseCase: NewsListUseCase

    private lateinit var underTest: NewsListViewModel

    @Test
    fun `LiveData Test`() {
        runBlocking {
            // GIVEN
            val topNews = listOf(
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
            val allNews = listOf(
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

            //val topResponse = NewsResponse(data = topNews)
            //val allResponse = NewsResponse(data = allNews)
            //whenever(newsListUseCase.getTopNewsUseCase().data).thenReturn(topNews.toNewsItem())
            //whenever(newsListUseCase.getAllNewsUseCase().data).thenReturn(allNews.toNewsItem())
            val list1 = newsListUseCase.getTopNewsUseCase().data
            val list2 = newsListUseCase.getAllNewsUseCase().data
            //whenever(newsListUseCase.fetchAllNews()).thenReturn(allResponse)

            // WHEN
            underTest = NewsListViewModel(newsListUseCase)
            val result = underTest.newsState.value.list

            // THEN
            assertThat(result).isEqualTo(list1)
            //assert(result == topNews + allNews)
        }
    }
}