package com.margarin.commonpregnancy.presentation.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.GetWeekUseCase
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.home.HomeStore.Intent
import com.margarin.commonpregnancy.presentation.home.HomeStore.Label
import com.margarin.commonpregnancy.presentation.home.HomeStore.State
import com.margarin.commonpregnancy.presentation.utils.ContentType
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeWeek(val weekNumber: Int) : Intent
        data class ClickOnDetails(val week: Week, val contentType: ContentType) : Intent
    }

    data class State(
        val week: Week
    )

    sealed interface Label {
        data class ClickOnDetails(val week: Week, val contentType: ContentType) : Label
    }
}

class HomeStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getWeekUseCase: GetWeekUseCase
) {

    fun create(): HomeStore =
        object : HomeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = State(week = Week()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {

        data class ChangeWeek(val week: Week) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeWeek -> {
                    scope.launch {
                        dispatch(Msg.ChangeWeek(getWeekUseCase(intent.weekNumber)))
                    }
                }

                is Intent.ClickOnDetails -> {
                    publish(
                        Label.ClickOnDetails(
                            week = intent.week,
                            contentType = intent.contentType
                        )
                    )
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeWeek -> {
                copy(
                    week = msg.week
                )
            }
        }
    }
}
