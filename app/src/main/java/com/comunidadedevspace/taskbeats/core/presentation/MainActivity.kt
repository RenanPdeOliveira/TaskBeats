package com.comunidadedevspace.taskbeats.core.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.news.presentation.NewsListViewPagerFragment
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.databinding.ActivityMainBinding
import com.comunidadedevspace.taskbeats.core.presentation.events.MainEvents
import com.comunidadedevspace.taskbeats.core.presentation.viewmodel.MainActivityViewModel
import com.comunidadedevspace.taskbeats.tasks.presentation.TaskListDetailActivity
import com.comunidadedevspace.taskbeats.tasks.presentation.TaskListViewPagerFragment
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private val taskListViewPagerFragment = TaskListViewPagerFragment.newInstance()
    private val newsListViewPagerFragment = NewsListViewPagerFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        defaultFragment(taskListViewPagerFragment)
        setUpUiEvent()
        setUpBottomNavigationView()
        setUpFab()
    }

    private fun setUpFab() {
        binding.fabAdd.setOnClickListener {
            viewModel.onEvent(MainEvents.OnAddTaskClick)
        }
    }

    private fun setUpBottomNavigationView() {
        with(binding) {
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(1).isEnabled = false
            bottomNavigationView.setOnItemSelectedListener { item ->
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
    }

    private fun setUpUiEvent() {
        lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "task_list_screen" -> showFragment(taskListViewPagerFragment)
                            "news_list_screen" -> showFragment(newsListViewPagerFragment)
                            "detail_screen" -> openTaskDetail()
                        }
                    }

                    else -> Unit
                }
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