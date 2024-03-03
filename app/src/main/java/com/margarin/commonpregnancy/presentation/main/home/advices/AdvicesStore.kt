package com.margarin.commonpregnancy.presentation.main.home.advices

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.margarin.commonpregnancy.domain.model.Term
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.domain.usecase.GetTimeOfStartPregnancyUseCase
import com.margarin.commonpregnancy.domain.usecase.GetWeekUseCase
import com.margarin.commonpregnancy.presentation.main.home.advices.AdvicesStore.Intent
import com.margarin.commonpregnancy.presentation.main.home.advices.AdvicesStore.Label
import com.margarin.commonpregnancy.presentation.main.home.advices.AdvicesStore.State
import com.margarin.commonpregnancy.presentation.utils.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AdvicesStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeWeek(val weekNumber: Int) : Intent
        data class ClickOnDetails(val week: Week, val contentType: ContentType) : Intent
    }

    data class State(
        val week: Week,
        val term: Term
    )

    sealed interface Label {
        data class ClickOnDetails(val week: Week, val contentType: ContentType) : Label
    }
}

class AdvicesStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getWeekUseCase: GetWeekUseCase,
    private val getTimeOfStartPregnancyUseCase: GetTimeOfStartPregnancyUseCase
) {

    fun create(): AdvicesStore =
        object : AdvicesStore, Store<Intent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = State(week = Week(), term = Term()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class TermLoaded(val term: Term) : Action
    }

    private sealed interface Msg {

        data class ChangeWeek(val week: Week) : Msg
        data class TermLoaded(val term: Term) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getTimeOfStartPregnancyUseCase().collect {
                    dispatch(Action.TermLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeWeek -> {
                    scope.launch(Dispatchers.IO) {
                        val week = getWeekUseCase(intent.weekNumber)
                        scope.launch(Dispatchers.Main) {
                            dispatch(Msg.ChangeWeek(week))
                        }
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

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.TermLoaded -> {
                    dispatch(Msg.TermLoaded(term = action.term))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeWeek -> {
                copy(week = msg.week)
            }

            is Msg.TermLoaded -> {
                copy(term = msg.term)
            }
        }
    }
}
