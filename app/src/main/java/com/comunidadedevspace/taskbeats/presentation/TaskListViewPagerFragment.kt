package com.comunidadedevspace.taskbeats.presentation

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListViewPagerBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.AdapterViewPager
import com.comunidadedevspace.taskbeats.presentation.events.TaskListViewPagerEvent
import com.comunidadedevspace.taskbeats.presentation.viewmodel.TaskListViewPagerViewModel
import com.comunidadedevspace.taskbeats.util.UiEvent
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class TaskListViewPagerFragment : Fragment() {

    private var _binding: FragmentTaskListViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewPagerViewModel by viewModels {
        TaskListViewPagerViewModel.getFactory(requireActivity().application)
    }

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

        val adapter = AdapterViewPager(this)

        adapter.getFragment(taskListFragment, taskListFavoriteFragment)

        binding.viewPager.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        showSnackBar(binding.root, event.message, event.action)
                    }

                    is UiEvent.ShowDialog -> {
                        showDialog(event.title, event.message)
                    }

                    else -> Unit
                }
            }
        }

        binding.tasksToolBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.deleteAll -> {
                    viewModel.onEvent(TaskListViewPagerEvent.OnDeleteAllButtonClick)
                    true
                }

                else -> false
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
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

    private fun showSnackBar(view: View, message: String, action: String?) {
        when (action) {
            "Close" -> {
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .setAction(action) {
                        it.isVisible = false
                    }
                    .setAnchorView(requireActivity().findViewById(R.id.fabAdd))
                    .show()
            }

            null -> {
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .setAnchorView(requireActivity().findViewById(R.id.fabAdd))
                    .show()
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.onEvent(TaskListViewPagerEvent.OnYesDialogButtonClick)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
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
        fun newInstance() = TaskListViewPagerFragment()
    }
}