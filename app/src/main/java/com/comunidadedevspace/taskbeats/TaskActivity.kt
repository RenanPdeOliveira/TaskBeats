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
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class TaskActivity : AppCompatActivity() {

    private var titleShow: taskItem? = null
    private var descShow: taskItem? = null

    lateinit var OpenTextTitle: TextView

    companion object {
        val detailTitle = "TITLE_EXTRA"
        val detailDesc = "DESC_EXTRA"

        // Certificando que passe todas as views para a pagina unica de item
        fun start(context: Context, task: taskItem?): Intent {
            val intent = Intent(context, TaskActivity::class.java)
                .apply {
                    putExtra(detailTitle, task)
                    putExtra(detailDesc, task)
                }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        OpenTextTitle = findViewById(R.id.OTtextViewTitle)
        val OpenTextDesc: TextView = findViewById(R.id.OTtextViewDesc)

        val etTitle: EditText = findViewById(R.id.editText_Title)
        val etDesc: EditText = findViewById(R.id.editText_Desc)
        val btnAdd: Button = findViewById(R.id.btn_Add)

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()

            if (title.isNotEmpty() && desc.isNotEmpty()) {
                addTask(title, desc)
            } else {
                showMessage(it, "All fields are required")
            }
        }

        // Recuperar task
        titleShow = intent.getSerializableExtra(detailTitle) as taskItem?
        descShow = intent.getSerializableExtra(detailDesc) as taskItem?

        // Setar novo texto na tela
        OpenTextTitle.text = titleShow?.title ?: "Add a task"
        OpenTextDesc.text = descShow?.desc
    }

    fun addTask(title: String, desc: String) {
        val newTask = taskItem(0, title, desc)
        actionButton(newTask, ActionType.CREATE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bar_delete -> {

                if (titleShow != null) {
                    actionButton(titleShow!!, ActionType.DELETE)
                } else {
                    showMessage(OpenTextTitle, "There is no item to delete")
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