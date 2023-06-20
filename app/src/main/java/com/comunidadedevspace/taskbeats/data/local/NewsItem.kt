package com.comunidadedevspace.taskbeats.data.local

import java.io.Serializable

data class NewsItem(
    val title: String,
    val imageUrl: String
) : Serializable
