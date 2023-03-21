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
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class TaskListUpdate : AppCompatActivity() {

    private var task: taskItem? = null

    companion object {
        val detailTask = "DETAIL_EXTRA"

        // Certificando que passe todas as views para a pagina unica de item
        fun start(context: Context, task: taskItem?): Intent {
            val intent = Intent(context, TaskListUpdate::class.java)
                .apply {
                    putExtra(detailTask, task)
                }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list_update)

        val etTitle: EditText = findViewById(R.id.editText_Title)
        val etDesc: EditText = findViewById(R.id.editText_Desc)
        val btnAdd: Button = findViewById(R.id.btn_Add)

        // Recuperar task
        task = intent.getSerializableExtra(detailTask) as taskItem?

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
        val newTask = taskItem(id, title, desc)
        actionButton(newTask, actionType)
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
                    Toast.makeText(this, "There is no item to delete!", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun actionButton(task: taskItem, actionType: ActionType) {
        val intent = Intent()
            .apply {
                val taskAction = TaskAction(task, actionType.name)
                putExtra(TASK_ACTION_RESULT, taskAction)
            }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }
}