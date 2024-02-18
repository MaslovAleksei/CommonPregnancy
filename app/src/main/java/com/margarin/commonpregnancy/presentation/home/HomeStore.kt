package com.margarin.commonpregnancy.presentation.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.GetWeekListUseCase
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.home.HomeStore.Intent
import com.margarin.commonpregnancy.presentation.home.HomeStore.Label
import com.margarin.commonpregnancy.presentation.home.HomeStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeWeek(val weekNumber: Int) : Intent
        data class ClickOnMotherDetails(val week: Week) : Intent
        data class ClickOnChildDetails(val week: Week) : Intent
        data class ClickOnAdvice(val week: Week) : Intent
    }

    data class State(
        val weekNumber: Int
    )

    sealed interface Label {
        data class ClickOnMotherDetails(val week: Week) : Label
        data class ClickOnChildDetails(val week: Week) : Label
        data class ClickOnAdvice(val week: Week) : Label
    }
}

class HomeStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getWeekListUseCase: GetWeekListUseCase
) {

    fun create(): HomeStore =
        object : HomeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = State(weekNumber = 0),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class WeekListLoaded(val weeks: List<Week>) : Action
    }

    private sealed interface Msg {

        data class WeekListLoaded(val weeks: List<Week>): Msg

        data class ChangeWeek(val weekNumber: Int) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.WeekListLoaded(getWeekListUseCase()))
            }
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeWeek -> {
                    dispatch(Msg.ChangeWeek(intent.weekNumber))
                }

                is Intent.ClickOnAdvice -> {
                    publish(Label.ClickOnMotherDetails(intent.week))
                }

                is Intent.ClickOnChildDetails -> {
                    publish(Label.ClickOnChildDetails(intent.week))
                }

                is Intent.ClickOnMotherDetails -> {
                    publish(Label.ClickOnAdvice(intent.week))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when(action) {
                is Action.WeekListLoaded -> {
                    dispatch(Msg.WeekListLoaded(action.weeks))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeWeek -> {
                copy(
                    weekNumber = msg.weekNumber
                )
            }

            is Msg.WeekListLoaded -> {
                copy(
                    weekNumber = 0
                )
            }
        }
    }
}
