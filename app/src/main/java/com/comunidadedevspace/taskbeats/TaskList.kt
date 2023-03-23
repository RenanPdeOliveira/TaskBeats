package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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

    private var adapter = taskListAdapter(::openListItemClicked)

    // Certificando qual item excluir
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            // Pegando resultado
            val data = it.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: taskItem = taskAction.task

            if (taskAction.actionType == ActionType.DELETE.name) {
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

            } else if (taskAction.actionType == ActionType.CREATE.name) {
                val newList = arrayListOf<taskItem>()
                    .apply {
                        addAll(list)
                    }

                newList.add(task)

                showMessage(linearLOEmpty, "You added the task: ${task.title}")

                if (newList.size != 0) {
                    linearLOEmpty.visibility = View.GONE
                }

                adapter.submitList(newList)

                list = newList
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list)

        val taskRecyclerView: RecyclerView = findViewById(R.id.recyclerViewTask)
        val btnAdd: FloatingActionButton = findViewById(R.id.btnAdd)

        linearLOEmpty = findViewById(R.id.linearLayoutEmpty)

        list.sortBy { it.title }

        adapter.submitList(list)

        taskRecyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            openTaskList()
        }
    }

    // Abre TaskActivity após clicar em algum item da lista. Deve conter um item
    private fun openListItemClicked(task: taskItem) {
        openTaskList(task)
    }

    //Abre TaskActivity após clicar no botão ADD. Pode ou não existir um item!
    private fun openTaskList(task: taskItem? = null) {
        val intent = TaskListUpdate.start(this, task)
        startForResult.launch(intent)
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }
}

// CRUD (Create, Read, Update, Delete)
enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(
    val task: taskItem,
    val actionType: String
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"