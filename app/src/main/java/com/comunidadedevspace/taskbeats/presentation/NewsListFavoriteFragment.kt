package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.NewsItem
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListFavoriteBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.NewsListAdapter
import com.comunidadedevspace.taskbeats.presentation.viewmodel.NewsListViewModel
import com.comunidadedevspace.taskbeats.presentation.viewmodel.ProvideViewModelFactory
import kotlinx.coroutines.launch

class NewsListFavoriteFragment : Fragment() {

    private var _binding: FragmentNewsListFavoriteBinding? = null
    private val binding get() = _binding!!

    private val adapter = NewsListAdapter()

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

        binding.recyclerViewNewsFavorite.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.allList.collect { allList ->
                    val newList = allList?.map { all ->
                        NewsItem(
                            title = all.title,
                            imageUrl = all.imageUrl,
                            isFavorite = true,
                            drawableResId = R.drawable.baseline_favorite_24
                        )
                    }
                    adapter.submitList(newList)
                }
            }
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