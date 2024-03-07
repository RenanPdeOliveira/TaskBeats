package com.comunidadedevspace.taskbeats.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListViewPagerBinding
import com.comunidadedevspace.taskbeats.news.presentation.adapter.ViewPagerNewsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class NewsListViewPagerFragment : Fragment() {

    private var _binding: FragmentNewsListViewPagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerNewsAdapter

    private val newsListFragment = NewsListFragment.newInstance()
    private val newsListFavoriteFragment = NewsListFavoriteFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPagerAdapter()
        setUpTabLayoutMediator()
    }

    private fun setUpViewPagerAdapter() {
        viewPagerAdapter = ViewPagerNewsAdapter(this)
        viewPagerAdapter.getNewsFragment(newsListFragment, newsListFavoriteFragment)
        binding.newsViewPager.adapter = viewPagerAdapter
    }

    private fun setUpTabLayoutMediator() {
        TabLayoutMediator(binding.newsTabLayout, binding.newsViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "News"
                }
                1 -> {
                    tab.text = "Favorites"
                }
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsListViewPagerFragment()
    }
}