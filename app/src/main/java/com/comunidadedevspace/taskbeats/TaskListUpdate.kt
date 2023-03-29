package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class TaskListUpdate : AppCompatActivity() {

    private var task: TaskItem? = null

    companion object {
        val detailTask = "DETAIL_EXTRA"

        // Certificando que passe todas as views para a pagina unica de item
        fun start(context: Context, task: TaskItem?): Intent {
            val intent = Intent(context, TaskListUpdate::class.java)
                .apply {
                    putExtra(detailTask, task)
                }
            return intent
        }
    }

    private lateinit var etTitle: EditText
    private lateinit var etDesc: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list_update)
        setSupportActionBar(findViewById(R.id.toolBar))

        etTitle = findViewById(R.id.editText_Title)
        etDesc = findViewById(R.id.editText_Desc)
        btnAdd = findViewById(R.id.btn_Add)

        // Recuperar task
        task = intent.getSerializableExtra(detailTask) as TaskItem?

        // Setar novo texto na tela
        if (task != null) {
            etTitle.setText(task?.title)
            etDesc.setText(task?.desc)
        }

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()

            if (title.isNotEmpty() && desc.isNotEmpty()) {
                if (task == null) {
                    AddorUpdateTask(0, title, desc, ActionType.CREATE)
                } else {
                    AddorUpdateTask(task!!.id, title, desc, ActionType.UPDATE)
                }
            } else {
                showMessage(it, "All fields are required")
            }
        }

    }

    fun AddorUpdateTask(id: Int, title: String, desc: String, actionType: ActionType) {
        val newTask = TaskItem(id, title, desc)
        actionButton(newTask, actionType)
    }

    private fun actionButton(task: TaskItem, actionType: ActionType) {
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bar_delete -> {
                if (task != null) {
                    actionButton(task!!, ActionType.DELETE)
                } else {
                    showMessage(etTitle, "There is no item to delete!")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }

}