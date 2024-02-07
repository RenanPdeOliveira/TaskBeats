package com.comunidadedevspace.taskbeats.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.databinding.ActivityMainBinding
import com.comunidadedevspace.taskbeats.presentation.events.MainEvents
import com.comunidadedevspace.taskbeats.presentation.viewmodel.MainActivityViewModel
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private val taskListViewPagerFragment = TaskListViewPagerFragment.newInstance()
    private val newsListFragment = NewsListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        defaultFragment(taskListViewPagerFragment)

        lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "task_list_screen" -> showFragment(taskListViewPagerFragment)
                            "news_list_screen" -> showFragment(newsListFragment)
                            "detail_screen" -> openTaskDetail()
                        }
                    }

                    else -> Unit
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            viewModel.onEvent(MainEvents.OnAddTaskClick)
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.listButton -> {
                    viewModel.onEvent(MainEvents.OnTaskListNavigationClick)
                    true
                }

                R.id.newsButton -> {
                    viewModel.onEvent(MainEvents.OnNewsListNavigationClick)
                    true
                }

                else -> false
            }
        }

    }

    private fun openTaskDetail() {
        val intent = TaskListDetailActivity.start(this, null)
        startActivity(intent)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun defaultFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
            setReorderingAllowed(true)
        }
    }

}