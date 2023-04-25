package com.comunidadedevspace.taskbeats.presentation

import com.comunidadedevspace.taskbeats.data.TaskItem
import java.io.Serializable

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(
    val task: TaskItem,
    val actionType: String
) : Serializable