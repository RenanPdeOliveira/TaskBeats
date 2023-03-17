package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Criando um adapter para o recyclerview
class taskListAdapter(
    private val openTaskView: (task: taskItem) -> Unit
) :
    RecyclerView.Adapter<taskListViewHolder>() {

    private lateinit var tasks: List<taskItem>

    fun submit(task: List<taskItem>) {
        tasks = task
        notifyDataSetChanged()
    }

    // Criando um view no recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return taskListViewHolder(view)
    }

    // Agrupando as views
    override fun onBindViewHolder(holder: taskListViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, openTaskView)
    }

    // Quantidade de views que queremos criar
    override fun getItemCount(): Int {
        return tasks.size
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