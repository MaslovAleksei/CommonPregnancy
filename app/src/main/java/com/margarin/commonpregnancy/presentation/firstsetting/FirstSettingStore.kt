package com.margarin.commonpregnancy.presentation.firstsetting

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.usecase.SaveTimeOfStartPregnancyUseCase
import com.margarin.commonpregnancy.presentation.firstsetting.FirstSettingStore.Intent
import com.margarin.commonpregnancy.presentation.firstsetting.FirstSettingStore.Label
import com.margarin.commonpregnancy.presentation.firstsetting.FirstSettingStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FirstSettingStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class SaveChanges(val timeStamp: Long) : Intent
        data class ChangeTerm(val timeStamp: Long) : Intent
        data object ChangeAgreementCheckState: Intent
    }

    data class State(
        val timeStamp: Long,
        val isTermChanged: Boolean,
        val isAgreed: Boolean = false
    )

    sealed interface Label {
        data object SaveChanges : Label
    }
}

class FirstSettingStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val saveTimeOfStartPregnancyUseCase: SaveTimeOfStartPregnancyUseCase
) {

    fun create(): FirstSettingStore =
        object : FirstSettingStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FirstSettingStore",
            initialState = State(
                isTermChanged = false,
                timeStamp = 0,
                isAgreed = false
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeTerm(val timeStamp: Long) : Msg
        data object ChangeAgreementCheckState: Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {}
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeTerm -> {
                    dispatch(Msg.ChangeTerm(timeStamp = intent.timeStamp))
                }

                is Intent.SaveChanges -> {
                    scope.launch {
                        saveTimeOfStartPregnancyUseCase(intent.timeStamp)
                        publish(Label.SaveChanges)
                    }
                }

                is Intent.ChangeAgreementCheckState -> {
                    dispatch(Msg.ChangeAgreementCheckState)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeTerm -> {
                copy(timeStamp = msg.timeStamp, isTermChanged = true)
            }

            is Msg.ChangeAgreementCheckState -> {
                copy(isAgreed = !isAgreed)
            }
        }
    }
}
