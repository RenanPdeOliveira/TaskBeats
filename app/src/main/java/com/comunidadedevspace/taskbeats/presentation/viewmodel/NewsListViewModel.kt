package com.comunidadedevspace.taskbeats.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsService
import com.comunidadedevspace.taskbeats.data.remote.RetrofitModule
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val newsService: NewsService
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<List<NewsDto>>()
    val newsLiveData: LiveData<List<NewsDto>> = _newsLiveData

    init {
        getNewsList()
    }

    private fun getNewsList() {
        viewModelScope.launch {
            try {
                val topNews = newsService.fetchTopNews().data
                val allNews = newsService.fetchAllNews().data
                _newsLiveData.value = topNews + allNews
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    companion object {
        fun create(): NewsListViewModel {
            val newsService = RetrofitModule.createNewsService()
            return NewsListViewModel(newsService)
        }
    }
}