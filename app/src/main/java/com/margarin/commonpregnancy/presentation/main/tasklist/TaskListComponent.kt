package com.margarin.commonpregnancy.presentation.main.tasklist

import com.margarin.commonpregnancy.domain.model.Task
import com.margarin.commonpregnancy.domain.model.TaskCategory
import kotlinx.coroutines.flow.StateFlow

interface TaskListComponent {

    val model: StateFlow<TaskListStore.State>

    fun onClickCheckBox(task: Task)

    fun onClickChangeVisibleState(
        taskCategory: TaskCategory,
        isVisible: Boolean
    )
}