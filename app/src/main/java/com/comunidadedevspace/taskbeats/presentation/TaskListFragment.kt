package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.databinding.FragmentTaskListBinding
import com.comunidadedevspace.taskbeats.presentation.events.TaskListEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private var adapter = TaskListAdapter(::openListItemClicked)

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTask.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        listUpdate()
    }

    private fun listUpdate() {
        viewModel.taskListLiveData.observe(this@TaskListFragment) { list ->
            if (list.isEmpty()) {
                binding.linearLayoutEmpty.visibility = View.VISIBLE
            } else {
                binding.linearLayoutEmpty.visibility = View.GONE
            }
            adapter.submitList(list)
        }
    }

    private fun openListItemClicked(task: TaskItem) {
        val intent = TaskListDetailActivity.start(requireContext(), task)
        requireActivity().startActivity(intent)
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