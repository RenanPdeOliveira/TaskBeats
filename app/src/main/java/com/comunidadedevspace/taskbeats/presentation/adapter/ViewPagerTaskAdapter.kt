package com.comunidadedevspace.taskbeats.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerTaskAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private lateinit var taskListFragment: Fragment
    private lateinit var taskListFavoriteFragment: Fragment

    fun getFragment(
        taskListFragment: Fragment,
        taskListFavoriteFragment: Fragment
    ) {
        this.taskListFragment = taskListFragment
        this.taskListFavoriteFragment = taskListFavoriteFragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> taskListFragment
            1 -> taskListFavoriteFragment
            else -> taskListFragment
        }
    }
}