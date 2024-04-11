package com.comunidadedevspace.taskbeats.tasks.presentation.task_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.presentation.components.showAlertDialog
import com.comunidadedevspace.taskbeats.core.presentation.components.showSnackBar
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListFavoriteBinding
import com.comunidadedevspace.taskbeats.tasks.presentation.adapter.TaskListAdapter
import com.comunidadedevspace.taskbeats.core.presentation.view_model_factory.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.core.util.UiEvent
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list_detail.TaskListDetailActivity
import kotlinx.coroutines.launch

class TaskListFavoriteFragment : Fragment() {

    private var _binding: FragmentTaskListFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TaskListViewModel> {
        ProvideViewModelFactory(requireActivity().application)
    }

    private var adapter = TaskListAdapter(::openListItemClicked, ::onDoneTaskClick, ::onFavoriteTaskClick, ::onDeleteTaskClick)

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
        setUpRecyclerViewAdapter()
        setUpUiEvent()
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

    private fun setUpRecyclerViewAdapter() {
        binding.recyclerViewTaskFavorite.adapter = adapter
    }

    private fun setUpUiEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "detail_screen" -> adapter =
                                TaskListAdapter(::openListItemClicked, ::onDoneTaskClick, ::onFavoriteTaskClick, ::onDeleteTaskClick)
                        }
                    }

                    is UiEvent.ShowSnackBar -> {
                        showSnackBar(
                            requireActivity(),
                            event.message,
                            event.action?.asString(requireActivity().applicationContext)
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun openListItemClicked(task: TaskItem) {
        val intent = TaskListDetailActivity.start(requireContext(), task)
        requireActivity().startActivity(intent)
    }

    private fun onFavoriteTaskClick(task: TaskItem) {
        viewModel.onEvent(
            TaskListEvents.OnFavoriteButtonClick(
                TaskItem(
                    id = task.id,
                    title = task.title,
                    desc = task.desc,
                    dateTime = task.dateTime,
                    isDone = task.isDone,
                    isFavorite = !task.isFavorite,
                    drawableResId = if (task.isFavorite) R.drawable.baseline_outline_grade_24 else R.drawable.baseline_grade_24
                )
            )
        )
    }

    private fun onDoneTaskClick(task: TaskItem) {
        viewModel.onEvent(
            TaskListEvents.OnDoneButtonClick(
                TaskItem(
                    id = task.id,
                    title = task.title,
                    desc = task.desc,
                    dateTime = task.dateTime,
                    isDone = !task.isDone,
                    isFavorite = task.isFavorite,
                    drawableResId = task.drawableResId
                )
            )
        )
    }

    private fun onDeleteTaskClick(task: TaskItem) {
        showAlertDialog(
            context = requireContext(),
            title = UiText.StringResource(R.string.dialog_title_delete),
            message = UiText.StringResource(R.string.dialog_title_message, task.title),
            positiveText = UiText.StringResource(R.string.dialog_title_positive),
            negativeText = UiText.StringResource(R.string.dialog_title_negative),
            onPositiveClick = { viewModel.onEvent(TaskListEvents.OnDeleteButtonClick(task)) }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskListFavoriteFragment()
    }
}