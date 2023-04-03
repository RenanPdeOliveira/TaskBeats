package com.comunidadedevspace.taskbeats.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var desc: String
) : Serializable
