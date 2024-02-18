package com.margarin.commonpregnancy.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultHomeComponent @AssistedInject constructor(
    private val storeFactory: HomeStoreFactory,
    @Assisted("onMotherDetailsClick") private val onMotherDetailsClick: (Week) -> Unit,
    @Assisted("onChildDetailsClick") private val onChildDetailsClick: (Week) -> Unit,
    @Assisted("onAdviceClick") private val onAdviceClick: (Week) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is HomeStore.Label.ClickOnAdvice -> {
                        onAdviceClick(it.week)
                    }

                    is HomeStore.Label.ClickOnChildDetails -> {
                        onChildDetailsClick(it.week)
                    }

                    is HomeStore.Label.ClickOnMotherDetails -> {
                        onMotherDetailsClick(it.week)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<HomeStore.State> = store.stateFlow

    override fun changeWeek(weekNumber: Int) {
        store.accept(HomeStore.Intent.ChangeWeek(weekNumber))
    }

    override fun onClickOnMotherDetails(week: Week) {
        store.accept(HomeStore.Intent.ClickOnMotherDetails(week))
    }

    override fun onClickOnChildDetails(week: Week) {
        store.accept(HomeStore.Intent.ClickOnChildDetails(week))
    }

    override fun onClickOnAdvice(week: Week) {
        store.accept(HomeStore.Intent.ClickOnAdvice(week))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onMotherDetailsClick") onMotherDetailsClick: (Week) -> Unit,
            @Assisted("onChildDetailsClick") onChildDetailsClick: (Week) -> Unit,
            @Assisted("onAdviceClick") onAdviceClick: (Week) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultHomeComponent

    }
}