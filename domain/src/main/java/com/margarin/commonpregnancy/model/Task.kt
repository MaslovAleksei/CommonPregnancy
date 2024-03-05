package com.margarin.commonpregnancy.model

data class Task(
    val value: String,
    val category: TaskCategory = TaskCategory.FirstTrimester
)
