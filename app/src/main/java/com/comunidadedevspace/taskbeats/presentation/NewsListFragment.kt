package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.NewsItem
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.NewsListAdapter
import com.comunidadedevspace.taskbeats.presentation.events.NewsListEvents
import com.comunidadedevspace.taskbeats.presentation.viewmodel.NewsListViewModel
import com.comunidadedevspace.taskbeats.presentation.viewmodel.ProvideViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val adapter = NewsListAdapter(::onFavoriteButtonClick)

    private val viewModel by viewModels<NewsListViewModel> {
        ProvideViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewNews.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsState.collect { state ->
                    val newList = state.list?.map { item ->
                        NewsItem(
                            id = item.id,
                            title = item.title,
                            imageUrl = item.imageUrl,
                            isFavorite = false,
                            drawableResId = R.drawable.baseline_favorite_border_24
                        )
                    }
                    adapter.submitList(newList)
                    withContext(Dispatchers.Main) {
                        binding.progressBarNewsList.isVisible = state.isLoading
                        binding.newsStateEmpty.isVisible = state.list?.size == 0
                    }
                }
            }
        }
    }

    private fun onFavoriteButtonClick(news: NewsItem) {
        if (!news.isFavorite) {
            viewModel.onEvent(
                NewsListEvents.OnAddFavorite(
                    NewsItem(
                        id = news.id,
                        title = news.title,
                        imageUrl = news.imageUrl,
                        isFavorite = true,
                        drawableResId = R.drawable.baseline_favorite_24
                    )
                )
            )
        } else {
            viewModel.onEvent(
                NewsListEvents.OnDeleteFavorite(
                    NewsItem(
                        id = news.id,
                        title = news.title,
                        imageUrl = news.imageUrl,
                        isFavorite = false,
                        drawableResId = R.drawable.baseline_favorite_border_24
                    )
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsListFragment()
    }
}