package com.margarin.commonpregnancy.presentation.main.tasklist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.margarin.commonpregnancy.domain.model.Task
import com.margarin.commonpregnancy.domain.model.TaskCategory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DefaultTaskListComponent @AssistedInject constructor(
    private val storeFactory: TaskListStoreFactory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : TaskListComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<TaskListStore.State> = store.stateFlow

    override fun onClickCheckBox(task: Task) {
        store.accept(TaskListStore.Intent.ClickChangeCompleteState(task = task))
    }

    override fun onClickChangeVisibleState(
        taskCategory: TaskCategory,
        isVisible: Boolean
    ) {
        store.accept(
            TaskListStore.Intent.ClickChangeCategoryVisibleState(
                taskCategory = taskCategory,
                isVisible = isVisible
            )
        )
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultTaskListComponent
    }
}