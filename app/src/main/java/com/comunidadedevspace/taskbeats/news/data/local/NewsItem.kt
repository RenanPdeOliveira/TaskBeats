package com.comunidadedevspace.taskbeats.news.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class NewsItem(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val drawableResId: Int
) : Parcelable
