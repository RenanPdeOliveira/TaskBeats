package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.databinding.FragmentNewsListViewPagerBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.ViewPagerNewsAdapter
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

        viewPagerAdapter = ViewPagerNewsAdapter(this)
        viewPagerAdapter.getNewsFragment(newsListFragment, newsListFavoriteFragment)
        binding.newsViewPager.adapter = viewPagerAdapter

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() = NewsListViewPagerFragment()
    }
}