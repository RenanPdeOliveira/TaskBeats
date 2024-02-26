package com.comunidadedevspace.taskbeats.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Parcelize
@Entity
data class TaskItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val dateTime: String,
    val isFavorite: Boolean,
    val drawableResId: Int
) : Parcelable
