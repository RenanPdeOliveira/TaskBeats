package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var list = arrayListOf(
        taskItem(0, "Mercado", "Comprar leite, maça e pão"),
        taskItem(1, "Festa", "Convidar os amigos para festa"),
        taskItem(2, "DevSpace", "Estudar RecyclerView"),
        taskItem(3, "Carro", "Abastecer carro")
    )

    private lateinit var linearLOEmpty: LinearLayout

    private var adapter = taskListAdapter(::openTaskView)

    // Certificando qual item excluir
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            // Pegando resultado
            val data = it.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: taskItem = taskAction.task

            val newList = arrayListOf<taskItem>()
                .apply {
                    addAll(list)
                }

            newList.remove(task)

            showMessage(linearLOEmpty, "You deleted the task: ${task.title}")

            if (newList.size == 0) {
                linearLOEmpty.visibility = View.VISIBLE
            }

            adapter.submitList(newList)

            list = newList
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskRecyclerView: RecyclerView = findViewById(R.id.recyclerViewTask)
        linearLOEmpty = findViewById(R.id.linearLayoutEmpty)

        list.sortBy { it.title }

        adapter.submitList(list)

        taskRecyclerView.adapter = adapter
    }

    // Abrindo pagina unica de cada item da lista
    private fun openTaskView(task: taskItem) {
        val intent = TaskActivity.start(this, task)
        startForResult.launch(intent)
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }
}

// Ações
sealed class ActionType : Serializable {
    object DELETE : ActionType()
    object EDIT : ActionType()
    object CREATE : ActionType()
}

data class TaskAction(
    val task: taskItem,
    val actionType: ActionType
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"