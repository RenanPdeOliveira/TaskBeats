package com.comunidadedevspace.taskbeats.news.presentation.news_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.domain.util.DataError
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.core.domain.util.Resource
import com.comunidadedevspace.taskbeats.core.domain.util.Result
import com.comunidadedevspace.taskbeats.news.presentation.state.NewsState
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain
import com.comunidadedevspace.taskbeats.news.domain.use_case.NewsListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun onEvent(event: NewsListEvents) {
        when (event) {
            is NewsListEvents.OnAddFavorite -> insertNews(event.news)
            is NewsListEvents.OnDeleteFavorite -> deleteNews(event.news)
        }
    }

    private fun insertNews(news: NewsDomain) = viewModelScope.launch {
        newsListUseCase.insertNewsUseCase(news)
    }

    private fun deleteNews(news: NewsDomain) = viewModelScope.launch {
        newsListUseCase.deleteNewsByIdUseCase(news)
    }

    fun getNewsList() = viewModelScope.launch {
        _newsState.update {
            it.copy(
                topNews = null,
                isLoading = true
            )
        }
        delay(2_000)

        /*when (val allNews = newsListUseCase.getAllNewsUseCase()) {
            is Resource.Success -> {
                _newsState.update {
                    it.copy(
                        list = allNews.data
                    )
                }
            }

            is Resource.Error -> {
                _uiEvent.send(UiEvent.ShowSnackBar(message = UiText.StringResource(R.string.snackbar_top_news_not_found)))
            }
        }*/
        when (val topNews = newsListUseCase.getTopNewsUseCase()) {

            is Result.Error -> {
                when (topNews.error) {
                    DataError.Network.TOP_NEWS_NOTY_FOUND_EXCEPTION -> {
                        _newsState.update { it.copy(isLoading = false) }
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_all_news_not_found)))
                    }

                    DataError.Network.TOP_NEWS_NOTY_FOUND_HTTP_EXCEPTION -> {
                        _newsState.update { it.copy(isLoading = false) }
                        _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.snackbar_all_news_not_found)))
                    }

                    else -> Unit
                }
            }

            is Result.Success -> {
                _newsState.update {
                    it.copy(
                        topNews = topNews.data,
                        isLoading = false
                    )
                }
                _uiEvent.send(UiEvent.ShowSnackBar(message = UiText.StringResource(R.string.snackbar_all_top_news_not_found)))
            }
        }
    }
}