package com.margarin.commonpregnancy.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.details.DetailsStore.Intent
import com.margarin.commonpregnancy.presentation.details.DetailsStore.Label
import com.margarin.commonpregnancy.presentation.details.DetailsStore.State

interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack: Intent
    }

    data class State(
        val week: Week
    )

    sealed interface Label {
        data object ClickBack: Label
    }
}

class DetailsStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(week: Week): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(week = week),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State {
            TODO("Not yet implemented")
        }
    }
}
