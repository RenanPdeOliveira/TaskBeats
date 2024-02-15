package com.comunidadedevspace.taskbeats.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.domain.repository.TaskRepository
import com.comunidadedevspace.taskbeats.domain.util.Resource
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _topList = MutableStateFlow<List<NewsDto>?>(emptyList())
    val topList: StateFlow<List<NewsDto>?> = _topList.asStateFlow()

    private val _allList = MutableStateFlow<List<NewsDto>?>(emptyList())
    val allList: StateFlow<List<NewsDto>?> = _allList.asStateFlow()

    init {
        getNewsList()
    }

    private fun getNewsList() {
        viewModelScope.launch {
            when (val result = repository.fetchTopNews()) {
                is Resource.Success -> {
                    _topList.value = result.data?.data
                }

                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Top news not found!"))
                }
            }
            when (val result = repository.fetchAllNews()) {
                is Resource.Success -> {
                    _allList.value = result.data?.data
                }

                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "All news not found!"))
                }
            }
        }
    }
}