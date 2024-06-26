package com.comunidadedevspace.taskbeats.tasks.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val dateTime: String,
    val isDone: Boolean,
    val isFavorite: Boolean,
    val drawableResId: Int
) : Parcelable
