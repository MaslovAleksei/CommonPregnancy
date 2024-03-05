package com.margarin.commonpregnancy

import com.margarin.commonpregnancy.model.Task
import com.margarin.commonpregnancy.model.TaskCategory
import kotlinx.coroutines.flow.StateFlow

interface TaskListComponent {

    val model: StateFlow<TaskListStore.State>

    fun onClickCheckBox(task: Task)

    fun onClickChangeVisibleState(
        taskCategory: TaskCategory,
        isVisible: Boolean
    )
}