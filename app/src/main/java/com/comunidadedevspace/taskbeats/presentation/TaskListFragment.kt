package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.TaskListAdapter
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.presentation.viewmodel.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.presentation.viewmodel.TaskListViewModel
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private var adapter = TaskListAdapter(::openListItemClicked, ::changeIsFavorite)

    private val viewModel by viewModels<TaskListViewModel> {
        ProvideViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTask.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "detail_screen" -> adapter =
                                TaskListAdapter(::openListItemClicked, ::changeIsFavorite)
                        }
                    }

                    else -> Unit
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        listUpdate()
    }

    private fun listUpdate() {
        viewModel.taskListLiveData.observe(this@TaskListFragment) { list ->
            binding.linearLayoutEmpty.isVisible = list.isEmpty()
            adapter.submitList(list)
        }
    }

    private fun openListItemClicked(task: TaskItem) {
        val intent = TaskListDetailActivity.start(requireContext(), task)
        requireActivity().startActivity(intent)
    }

    private fun changeIsFavorite(task: TaskItem) {
        viewModel.onEvent(
            TaskListEvents.OnFavoriteButtonClick(
                TaskItem(
                    task.id,
                    task.title,
                    task.desc,
                    !task.isFavorite,
                    if (task.isFavorite) R.drawable.baseline_outline_grade_24 else R.drawable.baseline_grade_24
                )
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment TaskListFragment.
         */
        @JvmStatic
        fun newInstance() = TaskListFragment()
    }

}