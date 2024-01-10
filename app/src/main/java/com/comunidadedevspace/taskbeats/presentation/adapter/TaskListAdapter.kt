package com.comunidadedevspace.taskbeats.presentation.adapter

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
import com.comunidadedevspace.taskbeats.databinding.ItemTaskBinding

class TaskListAdapter(
    private val openTaskView: (task: TaskItem) -> Unit,
    private val isFavorite: (task: TaskItem) -> Unit
) : ListAdapter<TaskItem, TaskListViewHolder>(TaskListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val itemBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, openTaskView, isFavorite)
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

class TaskListViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        task: TaskItem,
        openTaskView: (task: TaskItem) -> Unit,
        isFavorite: (task: TaskItem) -> Unit
    ) {
        binding.textViewTitle.text = task.title
        binding.textViewDesc.text = task.desc

        binding.root.setOnClickListener {
            openTaskView.invoke(task)
        }

        binding.imageIsFavorite.setOnClickListener {
            isFavorite.invoke(task)
            if (task.isFavorite) {
                binding.imageIsFavorite.setImageResource(R.drawable.baseline_outline_grade_24)
            } else {
                binding.imageIsFavorite.setImageResource(R.drawable.baseline_grade_24)
            }
        }
    }
}