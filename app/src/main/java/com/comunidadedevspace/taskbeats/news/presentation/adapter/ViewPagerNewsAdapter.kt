package com.comunidadedevspace.taskbeats.news.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerNewsAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private lateinit var newsListFragment: Fragment
    private lateinit var newsListFavoriteFragment: Fragment

    fun getNewsFragment(
        newsListFragment: Fragment,
        newsListFavoriteFragment: Fragment
    ) {
        this.newsListFragment = newsListFragment
        this.newsListFavoriteFragment = newsListFavoriteFragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> newsListFragment
            1 -> newsListFavoriteFragment
            else -> newsListFragment
        }
    }
}