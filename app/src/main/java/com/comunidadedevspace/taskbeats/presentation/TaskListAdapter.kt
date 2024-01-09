package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.TaskItem

class TaskListAdapter(
    private val openTaskView: (task: TaskItem) -> Unit
) : ListAdapter<TaskItem, TaskListViewHolder>(TaskListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, openTaskView)
    }

    private companion object : DiffUtil.ItemCallback<TaskItem>() {
        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.desc == newItem.desc &&
                    oldItem.isFavorite == newItem.isFavorite
        }

    }
}

class TaskListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val taskTitle: TextView = view.findViewById(R.id.textViewTitle)
    private val taskDesc: TextView = view.findViewById(R.id.textViewDesc)
    private var btnFavorite: ImageButton = view.findViewById(R.id.imageIsFavorite)
    private var btnNotFavorite: ImageButton = view.findViewById(R.id.imageIsNotFavorite)

    fun bind(
        task: TaskItem,
        openTaskView: (task: TaskItem) -> Unit
    ) {
        taskTitle.text = task.title
        taskDesc.text = task.desc

        view.setOnClickListener {
            openTaskView.invoke(task)
        }

        btnFavorite.setOnClickListener {
            if (task.isFavorite) {
                btnFavorite.visibility = View.VISIBLE
                btnNotFavorite.visibility = View.GONE
            } else {
                btnFavorite.visibility = View.GONE
                btnNotFavorite.visibility = View.VISIBLE
            }
        }

        btnNotFavorite.setOnClickListener {
            if (task.isFavorite) {
                btnFavorite.visibility = View.VISIBLE
                btnNotFavorite.visibility = View.GONE
            } else {
                btnFavorite.visibility = View.GONE
                btnNotFavorite.visibility = View.VISIBLE
            }
        }
    }
}