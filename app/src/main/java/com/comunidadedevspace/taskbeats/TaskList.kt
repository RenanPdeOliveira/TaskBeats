package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import java.io.Serializable
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var list = arrayListOf(
        taskItem(0, "Mercado", "Comprar leite, maça e pão"),
        taskItem(1, "Festa", "Convidar os amigos para festa"),
        taskItem(2, "DevSpace", "Estudar RecyclerView"),
        taskItem(3, "Carro", "Abastecer carro")
    )

    private lateinit var linearLOEmpty: LinearLayout
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var btnAdd: FloatingActionButton

    private val dataBase by lazy {
        Room.databaseBuilder(
            applicationContext, AppDataBase::class.java, "Data_Base_App"
        ).build()
    }

    private val dao by lazy {
        dataBase.taskDao()
    }

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

                deleteItem(task)
                showMessage(linearLOEmpty, "You deleted ${task.title}")

            } else if (taskAction.actionType == ActionType.CREATE.name) {

                addItem(task)
                showMessage(linearLOEmpty, "You added ${task.title}")

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list)

        listUpdate()

        taskRecyclerView = findViewById(R.id.recyclerViewTask)
        btnAdd = findViewById(R.id.btnAdd)
        linearLOEmpty = findViewById(R.id.linearLayoutEmpty)

        taskRecyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            openTaskList()
        }
    }

    private fun deleteItem(task: taskItem) {
        CoroutineScope(IO).launch {
            dao.delete(task)
            listUpdate()
        }
    }

    private fun addItem(task: taskItem) {
        CoroutineScope(IO).launch {
            dao.insert(task)
            listUpdate()
        }
    }

    private fun listUpdate() {
        CoroutineScope(IO).launch {
            val myDataBase: List<taskItem> = dao.getAll()
            adapter.submitList(myDataBase)
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