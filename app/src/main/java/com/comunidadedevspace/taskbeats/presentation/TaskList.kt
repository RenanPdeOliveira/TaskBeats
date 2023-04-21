package com.comunidadedevspace.taskbeats.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.TaskItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class TaskList : AppCompatActivity() {

    private lateinit var layoutEmpty: LinearLayout
    private lateinit var rvLayout: RecyclerView
    private lateinit var btnAdd: FloatingActionButton

    private var adapter = taskListAdapter(::openListItemClicked)

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(application)
    }

    // Certificando qual item atualizar
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

            // Pegando resultado
            val data = it.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction

            viewModel.actionCRUD(taskAction)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list)

        rvLayout = findViewById(R.id.recyclerViewLayout)
        btnAdd = findViewById(R.id.fabAdd)
        layoutEmpty = findViewById(R.id.linearLayoutEmpty)

        rvLayout.adapter = adapter

        btnAdd.setOnClickListener {
            openTaskList()
        }
    }

    override fun onStart() {
        super.onStart()

        listUpdate()
    }

    private fun listUpdate() {

        // Observer
        val observer = Observer<List<TaskItem>> { list ->
            if (list.isEmpty()) {
                layoutEmpty.visibility = View.VISIBLE
            } else {
                layoutEmpty.visibility = View.GONE
            }
            adapter.submitList(list)
        }

        // LiveData
        viewModel.taskListLiveData.observe(this@TaskList, observer)
    }

    // Abre TaskActivity após clicar em algum item da lista. Deve conter um item
    private fun openListItemClicked(task: TaskItem) {
        openTaskList(task)
    }

    //Abre TaskActivity após clicar no botão ADD. Pode ou não existir um item!
    private fun openTaskList(task: TaskItem? = null) {
        val intent = TaskListDetail.start(this, task)
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
    val task: TaskItem,
    val actionType: String
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"