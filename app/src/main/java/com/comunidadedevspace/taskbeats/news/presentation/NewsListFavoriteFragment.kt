package com.comunidadedevspace.taskbeats.news.presentation

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
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListFavoriteBinding
import com.comunidadedevspace.taskbeats.news.presentation.adapter.NewsListAdapter
import com.comunidadedevspace.taskbeats.news.presentation.events.NewsListEvents
import com.comunidadedevspace.taskbeats.core.presentation.viewmodel.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.news.presentation.viewmodel.NewsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListFavoriteFragment : Fragment() {

    private var _binding: FragmentNewsListFavoriteBinding? = null
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
        _binding = FragmentNewsListFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViewAdapter()
        collectDataFromViewModel()
    }

    private fun setUpRecyclerViewAdapter() {
        binding.recyclerViewNewsFavorite.adapter = adapter
    }

    private fun collectDataFromViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsListFavorite.collect { newsList ->
                    val newList = newsList.filter { it.isFavorite }
                    adapter.submitList(newList)
                    withContext(Dispatchers.Main) {
                        binding.newsStateEmptyFavorite.isVisible = newList.isEmpty()
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
        fun newInstance() = NewsListFavoriteFragment()
    }
}