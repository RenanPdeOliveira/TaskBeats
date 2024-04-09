package com.comunidadedevspace.taskbeats.core.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.comunidadedevspace.taskbeats.core.util.UiText
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showAlertDialog(
    context: Context,
    title: UiText.StringResource? = null,
    message: UiText.StringResource? = null,
    positiveText: UiText.StringResource? = null,
    negativeText: UiText.StringResource? = null,
    view: View? = null,
    onPositiveClick: () -> Unit
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title?.asString(context))
        .setMessage(message?.asString(context))
        .setView(view)
        .setPositiveButton(positiveText?.asString(context)) { _, _ ->
            onPositiveClick()
        }.setNegativeButton(negativeText?.asString(context)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}