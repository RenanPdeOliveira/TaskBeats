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
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val list = arrayListOf(
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

            // Removendo item da lista
            list.remove(task)

            if (list.size == 0) {
                linearLOEmpty.visibility = View.VISIBLE
            }

            adapter.submit(list)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskRecyclerView: RecyclerView = findViewById(R.id.recyclerViewTask)
        linearLOEmpty = findViewById(R.id.linearLayoutEmpty)

        list.sortBy { it.title }

        adapter.submit(list)

        taskRecyclerView.adapter = adapter
    }

    // Abrindo pagina unica de cada item da lista
    private fun openTaskView(task: taskItem) {
        val intent = TaskActivity.start(this, task)
        startForResult.launch(intent)
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