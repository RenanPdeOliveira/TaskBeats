package com.comunidadedevspace.taskbeats.tasks.presentation.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.databinding.ItemTaskBinding

class TaskListAdapter(
    private val openTaskView: (task: TaskItem) -> Unit,
    private val isDone: (task: TaskItem) -> Unit,
    private val isFavorite: (task: TaskItem) -> Unit,
    private val deleteTask: (task: TaskItem) -> Unit
) : ListAdapter<TaskItem, TaskListViewHolder>(TaskListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, openTaskView, isDone, isFavorite, deleteTask)
    }

    private companion object : DiffUtil.ItemCallback<TaskItem>() {
        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.desc == newItem.desc &&
                    oldItem.isFavorite == newItem.isFavorite &&
                    oldItem.drawableResId == newItem.drawableResId
        }

    }
}

class TaskListViewHolder(private val binding: ItemTaskBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        task: TaskItem,
        openTaskView: (task: TaskItem) -> Unit,
        isDone: (task: TaskItem) -> Unit,
        isFavorite: (task: TaskItem) -> Unit,
        deleteTask: (task: TaskItem) -> Unit
    ) {
        with(binding) {
            textViewTitle.text = if (task.isDone) strikeThroughText(task.title) else task.title
            textViewDesc.text = if (task.isDone) strikeThroughText(task.desc) else task.desc
            textViewDate.text =
                if (task.isDone) strikeThroughText("Last update: ${task.dateTime}") else "Last update: ${task.dateTime}"
            checkBoxIsDone.isChecked = task.isDone
            imageIsFavorite.setImageResource(task.drawableResId)

            root.setOnClickListener {
                openTaskView.invoke(task)
            }

            checkBoxIsDone.setOnClickListener {
                isDone.invoke(task)
            }

            imageIsFavorite.setOnClickListener {
                isFavorite.invoke(task)
            }

            imageButtonDelete.setOnClickListener {
                deleteTask.invoke(task)
            }
        }
    }

    private fun strikeThroughText(text: String): Spannable {
        val spannable = SpannableString(text)
        spannable.setSpan(StrikethroughSpan(), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}