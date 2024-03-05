package com.margarin.commonpregnancy.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "term")
data class TermDbModel(
    @PrimaryKey
    val id: Int = 0,
    val timeOfStartPregnancy: Long
    )