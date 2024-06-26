package com.comunidadedevspace.taskbeats.core.presentation.components

import android.app.Activity
import androidx.core.view.isVisible
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.core.presentation.main_activity.MainActivity
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.comunidadedevspace.taskbeats.tasks.presentation.task_list_detail.TaskListDetailActivity
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(
    activity: Activity,
    message: UiText.StringResource,
    action: String? = null
) {
    val snackBar = when (activity) {
        is MainActivity -> {
            Snackbar.make(
                activity.findViewById(R.id.fabAdd),
                message.asString(activity.applicationContext),
                Snackbar.LENGTH_LONG
            ).setAnchorView(activity.findViewById(R.id.bottomNavigationView))
        }

        is TaskListDetailActivity -> {
            Snackbar.make(
                activity.findViewById(R.id.updateButton),
                message.asString(activity.applicationContext),
                Snackbar.LENGTH_LONG
            ).setAnchorView(activity.findViewById(R.id.updateButton))
        }

        else -> {
            Snackbar.make(
                activity.findViewById(R.id.fabAdd),
                message.asString(activity.applicationContext),
                Snackbar.LENGTH_LONG
            ).setAnchorView(activity.findViewById(R.id.bottomNavigationView))
        }
    }

    when (action) {
         "Close" -> {
            snackBar.setAction(action) {
                it.isVisible = false
            }.show()
        }

        else -> snackBar.show()
    }
}