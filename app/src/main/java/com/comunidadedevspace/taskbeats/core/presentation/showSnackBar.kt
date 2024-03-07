package com.comunidadedevspace.taskbeats.core.presentation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.tasks.presentation.TaskListDetailActivity
import com.google.android.material.snackbar.Snackbar

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun showSnackBar(
    activity: Activity,
    message: String,
    action: String? = null
) {
    val snackBar = when (activity) {
        is MainActivity -> {
            Snackbar.make(
                activity.findViewById(R.id.fabAdd),
                message,
                Snackbar.LENGTH_LONG
            ).setAnchorView(activity.findViewById(R.id.bottomNavigationView))
        }

        is TaskListDetailActivity -> {
            Snackbar.make(
                activity.findViewById(R.id.updateButton),
                message,
                Snackbar.LENGTH_LONG
            ).setAnchorView(activity.findViewById(R.id.updateButton))
        }

        else -> {
            Snackbar.make(
                activity.findViewById(R.id.fabAdd),
                message,
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