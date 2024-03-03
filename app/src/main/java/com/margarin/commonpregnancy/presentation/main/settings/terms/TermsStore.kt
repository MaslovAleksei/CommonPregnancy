package com.margarin.commonpregnancy.presentation.main.settings.terms

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.usecase.GetTimeOfStartPregnancyUseCase
import com.margarin.commonpregnancy.domain.usecase.SaveTimeOfStartPregnancyUseCase
import com.margarin.commonpregnancy.presentation.main.settings.terms.TermsStore.Intent
import com.margarin.commonpregnancy.presentation.main.settings.terms.TermsStore.Label
import com.margarin.commonpregnancy.presentation.main.settings.terms.TermsStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface TermsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data class SaveChanges(val timeStamp: Long) : Intent
        data class ChangeTerm(val timeStamp: Long) : Intent
    }

    data class State(
        val isTermChanged: Boolean,
        val timeStamp: Long
    )

    sealed interface Label {
        data object ClickBack : Label
        data object SaveChanges : Label
    }
}

class TermsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val saveTimeOfStartPregnancyUseCase: SaveTimeOfStartPregnancyUseCase,
    private val getTimeOfStartPregnancyUseCase: GetTimeOfStartPregnancyUseCase
) {

    fun create(): TermsStore =
        object : TermsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "TermsStore",
            initialState = State(
                isTermChanged = false,
                timeStamp = 0
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class TimeStampLoaded(val timeStamp: Long, val isTermChanged: Boolean) : Action
    }

    private sealed interface Msg {
        data class ChangeTerm(val timeStamp: Long, val isTermChanged: Boolean) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(Dispatchers.IO) {
                val term = getTimeOfStartPregnancyUseCase()
                scope.launch(Dispatchers.Main) {
                   term.collect { term ->
                        dispatch(
                            Action.TimeStampLoaded(
                                timeStamp = term.timeOfStartPregnancy.timeInMillis,
                                isTermChanged = false
                            )
                        )
                    }
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                is Intent.ChangeTerm -> {
                    dispatch(Msg.ChangeTerm(timeStamp = intent.timeStamp, isTermChanged = true))
                }

                is Intent.SaveChanges -> {
                    scope.launch {
                        saveTimeOfStartPregnancyUseCase(intent.timeStamp)
                        publish(Label.SaveChanges)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.TimeStampLoaded -> {
                    dispatch(Msg.ChangeTerm(timeStamp = action.timeStamp, isTermChanged = false))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeTerm -> {
                copy(timeStamp = msg.timeStamp, isTermChanged = msg.isTermChanged)
            }
        }
    }
}
