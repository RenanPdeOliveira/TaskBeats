package com.comunidadedevspace.taskbeats.tasks.presentation.task_list_detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.presentation.components.showAlertDialog
import com.comunidadedevspace.taskbeats.core.presentation.components.showSnackBar
import com.comunidadedevspace.taskbeats.tasks.data.local.TaskItem
import com.comunidadedevspace.taskbeats.databinding.ActivityTaskListDetailBinding
import com.comunidadedevspace.taskbeats.core.presentation.view_model_factory.ProvideViewModelFactory
import com.comunidadedevspace.taskbeats.core.util.UiEvent
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
        private const val detailTask = "DETAIL_EXTRA"

        fun start(context: Context, task: TaskItem?): Intent {
            val intent = Intent(context, TaskListDetailActivity::class.java)
                .apply {
                    putExtra(detailTask, task)
                }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUiEvent()
        setUpTaskItemParcelize()
        setUpButtonClick()
        setUpToolBar()
    }

    private fun setUpButtonClick() {
        binding.updateButton.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDesc.text.toString()

            task?.let {
                viewModel.onEvent(
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
            } ?: viewModel.onEvent(
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
    }

    private fun setUpToolBar() {
        binding.detailToolBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_bar_delete -> {
                    viewModel.onEvent(DetailEvents.OnDeleteItemClick(task))
                    true
                }

                else -> false
            }
        }

        binding.detailToolBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setUpTaskItemParcelize() {
        task = intent.getParcelableExtra(detailTask, TaskItem::class.java)

        task?.let {
            binding.editTextTitle.setText(it.title)
            binding.editTextDesc.setText(it.desc)
            binding.detailToolBar.title = it.title
        }
    }

    private fun setUpUiEvent() {
        lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.Navigate -> {
                        when (event.route) {
                            "main_screen" -> finish()
                        }
                    }

                    is UiEvent.ShowSnackBar -> {
                        showSnackBar(
                            this@TaskListDetailActivity,
                            event.message,
                            event.action?.asString(applicationContext)
                        )
                    }

                    is UiEvent.ShowDialog -> {
                        event.task?.let {
                            showAlertDialog(
                                context = this@TaskListDetailActivity,
                                title = event.title,
                                message = event.message,
                                positiveText = event.positiveText,
                                negativeText = event.negativeText,
                                onPositiveClick = { viewModel.onEvent(DetailEvents.OnYesDialogButtonClick(it)) }
                            )
                        }
                    }
                }
            }
        }
    }
}