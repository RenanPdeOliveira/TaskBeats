package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.TaskItem

// Criando um adapter para o recyclerview
class taskListAdapter(
    private val openTaskView: (task: TaskItem) -> Unit
) : ListAdapter<TaskItem, taskListViewHolder>(taskListAdapter) {

    // Criando um view no recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return taskListViewHolder(view)
    }

    // Agrupando as views
    override fun onBindViewHolder(holder: taskListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, openTaskView)
    }

    private companion object : DiffUtil.ItemCallback<TaskItem>() {
        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.title == newItem.title && oldItem.desc == newItem.desc
        }

    }
}

// Criando um viewholder no recyclerview
class taskListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val taskTitle: TextView = view.findViewById(R.id.textViewTitle)
    val taskDesc: TextView = view.findViewById(R.id.textViewDesc)

    fun bind(
        task: TaskItem,
        openTaskView: (task: TaskItem) -> Unit
    ) {
        taskTitle.text = task.title
        taskDesc.text = task.desc

        view.setOnClickListener {
            openTaskView.invoke(task)
        }
    }
}