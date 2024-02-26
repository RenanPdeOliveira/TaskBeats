package com.comunidadedevspace.taskbeats.presentation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.entity.TaskItem
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListFavoriteBinding
import com.comunidadedevspace.taskbeats.presentation.adapter.TaskListAdapter
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.presentation.viewmodel.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.presentation.viewmodel.TaskListViewModel
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TaskListFavoriteFragment : Fragment() {

    private var _binding: FragmentTaskListFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TaskListViewModel> {
        ProvideViewModelFactory(requireActivity().application)
    }

    private var adapter = TaskListAdapter(::openListItemClicked, ::changeIsFavorite, ::deleteTask)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTaskFavorite.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "detail_screen" -> adapter =
                                TaskListAdapter(::openListItemClicked, ::changeIsFavorite, ::deleteTask)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getFavoriteList()
    }

    private fun getFavoriteList() {
        viewModel.taskList.observe(viewLifecycleOwner) { list ->
            val newList = list.filter { it.isFavorite }
            binding.linearLayoutEmpty.isVisible = newList.isEmpty()
            adapter.submitList(newList)
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
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm")),
                    !task.isFavorite,
                    if (task.isFavorite) R.drawable.baseline_outline_grade_24 else R.drawable.baseline_grade_24
                )
            )
        )
    }

    private fun deleteTask(task: TaskItem) {
        viewModel.onEvent(TaskListEvents.OnDeleteButtonClick(task))
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
        fun newInstance() = TaskListFavoriteFragment()
    }
}