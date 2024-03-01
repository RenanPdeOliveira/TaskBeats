package com.comunidadedevspace.taskbeats.tasks.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.tasks.data.TaskItem
import com.comunidadedevspace.taskbeats.databinding.ActivityTaskListDetailBinding
import com.comunidadedevspace.taskbeats.tasks.presentation.events.DetailEvents
import com.comunidadedevspace.taskbeats.core.presentation.viewmodel.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.tasks.presentation.viewmodel.TaskListDetailViewModel
import com.comunidadedevspace.taskbeats.util.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListDetailBinding

    private var task: TaskItem? = null

    private val viewModel by viewModels<TaskListDetailViewModel> {
        ProvideViewModelFactory(application)
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
                        showMessage(binding.root, event.message, event.action)
                    }

                    is UiEvent.ShowDialog -> {
                        event.task?.let {
                            showDialog(
                                title = event.title,
                                message = event.message,
                                task = it
                            )
                        }
                    }
                }
            }
        }

        task = intent.getParcelableExtra(detailTask, TaskItem::class.java)

        task?.let {
            binding.editTextTitle.setText(it.title)
            binding.editTextDesc.setText(it.desc)
            binding.detailToolBar.title = it.title
        }

        binding.updateButton.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDesc.text.toString()

            task?.let {
                viewModel.actionCRUD(
                    DetailEvents.OnEditItemClick(
                        TaskItem(
                            id = it.id,
                            title = title,
                            desc = desc,
                            dateTime = LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm")),
                            isDone = it.isDone,
                            isFavorite = it.isFavorite,
                            drawableResId = it.drawableResId
                        )
                    )
                )
            } ?: viewModel.actionCRUD(
                DetailEvents.OnAddItemClick(
                    TaskItem(
                        id = 0,
                        title = title,
                        desc = desc,
                        dateTime = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm")),
                        isDone = false,
                        isFavorite = false,
                        drawableResId = R.drawable.baseline_outline_grade_24
                    )
                )
            )
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

    private fun showMessage(view: View, message: String, action: String? = null) {
        when (action) {
            "Close" -> {
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .setAction(action) {
                        it.isVisible = false
                    }
                    .show()
            }

            null -> {
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show()
            }
        }

    }

    private fun showDialog(title: String, message: String, task: TaskItem) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.actionCRUD(DetailEvents.OnYesDialogButtonClick(task))
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}