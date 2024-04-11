package com.comunidadedevspace.taskbeats.tasks.presentation.task_list_view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.presentation.components.showAlertDialog
import com.comunidadedevspace.taskbeats.core.presentation.components.showSnackBar
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListViewPagerBinding
import com.comunidadedevspace.taskbeats.tasks.presentation.adapter.ViewPagerTaskAdapter
import com.comunidadedevspace.taskbeats.core.presentation.view_model_factory.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list.TaskListFavoriteFragment
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list.TaskListFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

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
                        showSnackBar(
                            requireActivity(),
                            event.message,
                            event.action?.asString(requireActivity().applicationContext)
                        )
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