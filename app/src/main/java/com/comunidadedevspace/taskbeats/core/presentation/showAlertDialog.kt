package com.comunidadedevspace.taskbeats.core.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.comunidadedevspace.taskbeats.tasks.data.TaskItem

fun showAlertDialog(
    context: Context,
    title: String? = null,
    message: String? = null,
    positiveText: String? = null,
    negativeText: String? = null,
    view: View? = null,
    onPositiveClick: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setView(view)
        .setPositiveButton(positiveText) { _, _ ->
            onPositiveClick()
        }.setNegativeButton(negativeText) { dialog, _ ->
            dialog.dismiss()
        }.create()
        .show()
}