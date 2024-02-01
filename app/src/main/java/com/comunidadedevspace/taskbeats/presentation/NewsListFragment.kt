package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comunidadedevspace.taskbeats.data.local.NewsItem
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.NewsListAdapter
import com.comunidadedevspace.taskbeats.presentation.viewmodel.NewsListViewModel

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val adapter = NewsListAdapter()

    private val viewModel by lazy {
        NewsListViewModel.create()
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

        viewModel.newsLiveData.observe(viewLifecycleOwner) { newsDto ->
            val newsItem = newsDto.map { item ->
                NewsItem(
                    title = item.title,
                    imageUrl = item.imageUrl
                )
            }
            adapter.submitList(newsItem)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment NewsListFragment.
         */
        @JvmStatic
        fun newInstance() = NewsListFragment()
    }

}