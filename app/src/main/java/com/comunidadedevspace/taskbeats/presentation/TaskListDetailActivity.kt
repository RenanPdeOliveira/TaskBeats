package com.comunidadedevspace.taskbeats.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.TaskItem
import com.comunidadedevspace.taskbeats.databinding.ActivityTaskListDetailBinding
import com.comunidadedevspace.taskbeats.presentation.events.DetailEvents
import com.comunidadedevspace.taskbeats.util.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class TaskListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListDetailBinding

    private var task: TaskItem? = null

    private val viewModel: TaskListDetailViewModel by viewModels {
        TaskListDetailViewModel.getFactoryViewModel(application)
    }

    companion object {
        const val detailTask = "DETAIL_EXTRA"

        fun start(context: Context, task: TaskItem?): Intent {
            val intent = Intent(context, TaskListDetailActivity::class.java)
                .apply {
                    putExtra(detailTask, task)
                }
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "main_screen" -> finish()
                        }
                    }

                    is UiEvent.ShowSnackBar -> {
                        showMessage(binding.root, event.message)
                    }
                }
            }
        }

        task = intent.getParcelableExtra(detailTask, TaskItem::class.java)

        task?.let {
            binding.editTextTitle.setText(task?.title)
            binding.editTextDesc.setText(task?.desc)
        }

        binding.updateButton.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDesc.text.toString()

            task?.let {
                viewModel.actionCRUD(
                    DetailEvents.OnEditItemClick(
                        TaskItem(
                            it.id,
                            title,
                            desc,
                            it.isFavorite
                        )
                    )
                )
            } ?: viewModel.actionCRUD(DetailEvents.OnAddItemClick(TaskItem(0, title, desc, false)))
        }

        binding.detailToolBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_bar_delete -> {
                    viewModel.actionCRUD(DetailEvents.OnDeleteItemClick(task))
                    true
                }

                else -> false
            }
        }

        binding.detailToolBar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()
    }

}