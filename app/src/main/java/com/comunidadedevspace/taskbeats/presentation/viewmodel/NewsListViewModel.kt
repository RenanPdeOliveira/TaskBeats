package com.comunidadedevspace.taskbeats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.local.entity.NewsItem
import com.comunidadedevspace.taskbeats.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.domain.util.Resource
import com.comunidadedevspace.taskbeats.presentation.NewsState
import com.comunidadedevspace.taskbeats.presentation.events.NewsListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val newsListFavorite = repository.getAllNews()

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
        repository.insertNews(news)
    }

    private fun deleteNews(news: NewsItem) = viewModelScope.launch {
        repository.deleteNewsById(news.id)
    }

    private fun getNewsList() = viewModelScope.launch {
        _newsState.update { it.copy(isLoading = true) }

        when (val result = repository.fetchTopNews()) {
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
        when (val result = repository.fetchAllNews()) {
            is Resource.Success -> {
                _newsState.update {
                    it.copy(
                        allNews = result.data,
                        isLoading = false
                    )
                }
            }

            is Resource.Error -> {
                _uiEvent.send(UiEvent.ShowSnackBar(message = "All news not found!"))
                _newsState.update { it.copy(isLoading = false) }
            }
        }
    }
}