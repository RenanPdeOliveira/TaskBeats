package com.comunidadedevspace.taskbeats.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val drawableResId: Int
) : Parcelable
