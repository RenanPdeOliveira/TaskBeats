package com.comunidadedevspace.taskbeats

import java.io.Serializable

data class taskItem(
    val id: Int,
    val title: String,
    val desc: String
) : Serializable
