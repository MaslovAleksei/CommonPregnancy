package com.margarin.commonpregnancy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDbModel(
    @PrimaryKey
    val value: String
)
