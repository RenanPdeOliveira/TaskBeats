package com.comunidadedevspace.taskbeats

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class taskItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var desc: String
) : Serializable
