package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.TaskItem

/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment() {

    private lateinit var layoutEmpty: LinearLayout
    private lateinit var rvLayout: RecyclerView

    private var adapter = taskListAdapter(::openListItemClicked)

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLayout = view.findViewById(R.id.recyclerViewLayout)
        layoutEmpty = view.findViewById(R.id.linearLayoutEmpty)

        rvLayout.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        listUpdate()
    }

    private fun listUpdate() {

        // Observer
        val observer = Observer<List<TaskItem>> { list ->
            if (list.isEmpty()) {
                layoutEmpty.visibility = View.VISIBLE
            } else {
                layoutEmpty.visibility = View.GONE
            }
            adapter.submitList(list)
        }

        // LiveData
        viewModel.taskListLiveData.observe(this@TaskListFragment, observer)
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