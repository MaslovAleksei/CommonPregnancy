package com.margarin.commonpregnancy.setting


import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.setting.SettingStore.Intent
import com.margarin.commonpregnancy.setting.SettingStore.Label
import com.margarin.commonpregnancy.setting.SettingStore.State
import javax.inject.Inject

interface SettingStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickOnTerms : Intent
    }

    data class State(
        val setting: Unit
    )

    sealed interface Label {
        data object ClickOnTerms : Label
    }
}

class SettingStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory
) {

    fun create(): SettingStore =
        object : SettingStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SettingStore",
            initialState = State(Unit),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ClickOnTerms -> {
                    publish(
                        Label.ClickOnTerms
                    )
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
