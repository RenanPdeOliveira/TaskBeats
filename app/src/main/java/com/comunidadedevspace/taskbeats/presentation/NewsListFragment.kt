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
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.NewsListAdapter
import com.comunidadedevspace.taskbeats.presentation.viewmodel.NewsListViewModel
import com.comunidadedevspace.taskbeats.presentation.viewmodel.ProvideViewModelFactory
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
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
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewNews.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.topList.collect { topList ->
                    val newList = topList?.map { top ->
                        NewsItem(
                            title = top.title,
                            imageUrl = top.imageUrl,
                            isFavorite = false,
                            drawableResId = R.drawable.baseline_favorite_border_24
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
        fun newInstance() = NewsListFragment()
    }
}