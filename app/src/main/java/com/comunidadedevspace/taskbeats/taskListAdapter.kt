package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter

// Criando um adapter para o recyclerview
class taskListAdapter(
    private val openTaskView: (task: taskItem) -> Unit
) : ListAdapter<taskItem, taskListViewHolder>(taskListAdapter) {

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

    private companion object : DiffUtil.ItemCallback<taskItem>() {
        override fun areItemsTheSame(oldItem: taskItem, newItem: taskItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: taskItem, newItem: taskItem): Boolean {
            return oldItem.title == newItem.title && oldItem.desc == newItem.desc
        }

    }
}

// Criando um viewholder no recyclerview
class taskListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val taskTitle: TextView = view.findViewById(R.id.textViewTitle)
    val taskDesc: TextView = view.findViewById(R.id.textViewDesc)

    fun bind(
        task: taskItem,
        openTaskView: (task: taskItem) -> Unit
    ) {
        taskTitle.text = task.title
        taskDesc.text = task.desc

        view.setOnClickListener {
            openTaskView.invoke(task)
        }
    }
}