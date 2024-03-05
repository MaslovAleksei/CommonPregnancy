package com.margarin.commonpregnancy.presentation.main.tasklist

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.model.Task
import com.margarin.commonpregnancy.domain.model.TaskCategory
import com.margarin.commonpregnancy.domain.usecase.ChangeCompletedStateUseCase
import com.margarin.commonpregnancy.domain.usecase.CheckIsTaskCompletedUseCase
import com.margarin.commonpregnancy.domain.usecase.GetTaskListUseCase
import com.margarin.commonpregnancy.presentation.main.tasklist.TaskListStore.Intent
import com.margarin.commonpregnancy.presentation.main.tasklist.TaskListStore.Label
import com.margarin.commonpregnancy.presentation.main.tasklist.TaskListStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface TaskListStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class ClickChangeCompleteState(val task: Task) : Intent

        data class ClickChangeCategoryVisibleState(
            val taskCategory: TaskCategory,
            val isVisible: Boolean
        ) : Intent
    }

    data class State(

        val taskItems: List<TaskItem>,
        val taskCategoryItems: List<TaskCategoryItem>
    ) {
        data class TaskItem(
            val task: Task,
            val isCompleted: Boolean = false
        )

        data class TaskCategoryItem(
            val taskCategory: TaskCategory,
            val isVisible: Boolean = false
        )
    }

    sealed interface Label
}

class TaskListStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val checkIsTaskCompletedUseCase: CheckIsTaskCompletedUseCase,
    private val changeCompletedStateUseCase: ChangeCompletedStateUseCase,
    private val getTaskListUseCase: GetTaskListUseCase
) {

    fun create(): TaskListStore =
        object : TaskListStore, Store<Intent, State, Label> by storeFactory.create(
            name = "TaskListStore",
            initialState = State(listOf(), listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class TaskListLoaded(val tasks: List<Task>) : Action
    }

    private sealed interface Msg {
        data class TaskListLoaded(val tasks: List<Task>) : Msg

        data class CompletedStateLoaded(
            val value: String,
            val isCompleted: Boolean
        ) : Msg

        data class ChangeCompleteState(
            val task: Task,
            val isCompleted: Boolean
        ) : Msg

        data class ChangeCategoryVisibleState(
            val taskCategory: TaskCategory,
            val isVisible: Boolean
        ) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(
                    Action.TaskListLoaded(getTaskListUseCase())
                )
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ClickChangeCompleteState -> {
                    scope.launch {
                        val task = intent.task
                        val isCompleted = if (checkIsTaskCompletedUseCase(value = task.value)) {
                            changeCompletedStateUseCase.removeFromCompleted(value = task.value)
                            false
                        } else {
                            changeCompletedStateUseCase.addToCompleted(task = task)
                            true
                        }
                        dispatch(
                            Msg.ChangeCompleteState(
                                task = task,
                                isCompleted = isCompleted
                            )
                        )
                    }
                }

                is Intent.ClickChangeCategoryVisibleState -> {
                    scope.launch {
                        dispatch(
                            Msg.ChangeCategoryVisibleState(
                                taskCategory = intent.taskCategory,
                                isVisible = !intent.isVisible
                            )
                        )
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.TaskListLoaded -> {
                    val tasks = action.tasks
                    dispatch(Msg.TaskListLoaded(tasks))
                    tasks.forEach { task ->
                        scope.launch {
                            dispatch(
                                Msg.CompletedStateLoaded(
                                    value = task.value,
                                    isCompleted = checkIsTaskCompletedUseCase(task.value)
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.TaskListLoaded -> {
                copy(
                    taskItems = msg.tasks.map {
                        State.TaskItem(task = it)
                    },
                    taskCategoryItems = listOf(
                        State.TaskCategoryItem(taskCategory = TaskCategory.FirstTrimester),
                        State.TaskCategoryItem(taskCategory = TaskCategory.SecondTrimester),
                        State.TaskCategoryItem(taskCategory = TaskCategory.ThirdTrimester),
                        State.TaskCategoryItem(taskCategory = TaskCategory.ThingsForHospital),
                        State.TaskCategoryItem(taskCategory = TaskCategory.ThingsAfterBirth),
                        State.TaskCategoryItem(taskCategory = TaskCategory.ThingsForDischarge)
                    )
                )
            }

            is Msg.CompletedStateLoaded -> {
                copy(
                    taskItems = taskItems.map {
                        if (it.task.value == msg.value) {
                            it.copy(isCompleted = msg.isCompleted)
                        } else {
                            it
                        }
                    }
                )
            }

            is Msg.ChangeCompleteState -> {
                copy(
                    taskItems = taskItems.map {
                        if (it.task == msg.task) {
                            it.copy(isCompleted = msg.isCompleted)
                        } else {
                            it
                        }
                    }
                )
            }

            is Msg.ChangeCategoryVisibleState -> {
                copy(
                    taskCategoryItems = taskCategoryItems.map {
                        if (it.taskCategory == msg.taskCategory) {
                            it.copy(
                                isVisible = msg.isVisible
                            )
                        } else {
                            it
                        }
                    }
                )
            }
        }
    }
}
