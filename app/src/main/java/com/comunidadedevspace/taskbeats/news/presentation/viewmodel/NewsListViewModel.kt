package com.comunidadedevspace.taskbeats.news.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.core.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.core.domain.util.Resource
import com.comunidadedevspace.taskbeats.news.presentation.NewsState
import com.comunidadedevspace.taskbeats.news.presentation.events.NewsListEvents
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.news.domain.usecase.NewsListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsListUseCase: NewsListUseCase
) : ViewModel() {

    val newsListFavorite = newsListUseCase.getAllFavoriteNewsUseCase()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _newsState = MutableStateFlow(NewsState())
    val newsState = _newsState.asStateFlow()

    init {
        getNewsList()
    }

    fun onEvent(event: NewsListEvents) {
        when (event) {
            is NewsListEvents.OnAddFavorite -> insertNews(event.news)
            is NewsListEvents.OnDeleteFavorite -> deleteNews(event.news)
        }
    }

    private fun insertNews(news: NewsItem) = viewModelScope.launch {
        newsListUseCase.insertNewsUseCase(news)
    }

    private fun deleteNews(news: NewsItem) = viewModelScope.launch {
        newsListUseCase.deleteNewsByIdUseCase(news)
    }

    private fun getNewsList() = viewModelScope.launch {
        _newsState.update { it.copy(isLoading = true) }

        when (val result = newsListUseCase.getAllNewsUseCase()) {
            is Resource.Success -> {
                _newsState.update {
                    it.copy(
                        list = result.data
                    )
                }
            }

            is Resource.Error -> {
                _uiEvent.send(UiEvent.ShowSnackBar(message = "Top news not found!"))
            }
        }
        when (val result = newsListUseCase.getTopNewsUseCase()) {
            is Resource.Success -> {
                _newsState.update {
                    it.copy(
                        allNews = result.data,
                        isLoading = false
                    )
                }
                _uiEvent.send(UiEvent.ShowSnackBar(message = "All and Top news found!"))
            }

            is Resource.Error -> {
                _uiEvent.send(UiEvent.ShowSnackBar(message = "All news not found!"))
                _newsState.update { it.copy(isLoading = false) }
            }
        }
    }
}