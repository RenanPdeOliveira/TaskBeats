package com.comunidadedevspace.taskbeats.tasks.presentation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.presentation.showAlertDialog
import com.comunidadedevspace.taskbeats.core.presentation.showSnackBar
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListViewPagerBinding
import com.comunidadedevspace.taskbeats.tasks.presentation.adapter.ViewPagerTaskAdapter
import com.comunidadedevspace.taskbeats.tasks.presentation.events.TaskListViewPagerEvent
import com.comunidadedevspace.taskbeats.core.presentation.viewmodel.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListViewPagerViewModel
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class TaskListViewPagerFragment : Fragment() {

    private var _binding: FragmentTaskListViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TaskListViewPagerViewModel> {
        ProvideViewModelFactory(requireActivity().application)
    }

    private lateinit var viewPagerAdapter: ViewPagerTaskAdapter

    private val taskListFragment = TaskListFragment.newInstance()
    private val taskListFavoriteFragment = TaskListFavoriteFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPagerAdapter()
        setUpUiEvent()
        setUpTabLayoutMediator()
        setUpToolBar()
    }

    private fun setUpViewPagerAdapter() {
        viewPagerAdapter = ViewPagerTaskAdapter(this)
        viewPagerAdapter.getFragment(taskListFragment, taskListFavoriteFragment)
        binding.taskViewPager.adapter = viewPagerAdapter
    }

    private fun setUpTabLayoutMediator() {
        TabLayoutMediator(binding.taskTabLayout, binding.taskViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Tasks"
                }

                1 -> {
                    tab.text = "Favorites"
                }
            }
        }.attach()
    }

    private fun setUpToolBar() {
        binding.tasksToolBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.deleteAll -> {
                    viewModel.onEvent(TaskListViewPagerEvent.OnDeleteAllButtonClick)
                    true
                }

                else -> false
            }
        }
    }

    private fun setUpUiEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        showSnackBar(requireActivity(), event.message, event.action)
                    }

                    is UiEvent.ShowDialog -> {
                        showAlertDialog(
                            context = requireContext(),
                            title = event.title,
                            message = event.message,
                            positiveText = event.positiveText,
                            negativeText = event.negativeText,
                            onPositiveClick = { viewModel.onEvent(TaskListViewPagerEvent.OnYesDialogButtonClick) }
                        )
                    }

                    else -> Unit
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
        fun newInstance() = TaskListViewPagerFragment()
    }
}