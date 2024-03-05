package com.margarin.commonpregnancy.domain.model

data class Task(
    val value: String,
    val category: TaskCategory = TaskCategory.FirstTrimester
)
