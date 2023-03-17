package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

class TaskActivity : AppCompatActivity() {

    private lateinit var titleShow: taskItem
    private lateinit var descShow: taskItem

    companion object {
        val detailTitle = "TITLE_EXTRA"
        val detailDesc = "DESC_EXTRA"

        // Certificando que passe todas as views para a pagina unica de item
        fun start(context: Context, task: taskItem): Intent {
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

        val OpenTextTitle: TextView = findViewById(R.id.OTtextViewTitle)
        val OpenTextDesc: TextView = findViewById(R.id.OTtextViewDesc)

        titleShow = requireNotNull(intent.getSerializableExtra(detailTitle) as taskItem?)
        descShow = requireNotNull(intent.getSerializableExtra(detailDesc) as taskItem?)

        OpenTextTitle.text = titleShow.title
        OpenTextDesc.text = descShow.desc
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bar_delete -> {
                val intent = Intent()
                    .apply {
                        val actionType = ActionType.DELETE
                        val taskAction = TaskAction(titleShow, actionType)
                        putExtra(TASK_ACTION_RESULT, taskAction)
                    }
                setResult(Activity.RESULT_OK, intent)
                finish()
                Toast.makeText(this, "You deleted this item", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}