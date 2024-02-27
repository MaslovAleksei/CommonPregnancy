package com.margarin.commonpregnancy.presentation.main.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.utils.componentScope
import com.margarin.commonpregnancy.presentation.utils.ContentType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultHomeComponent @AssistedInject constructor(
    private val storeFactory: HomeStoreFactory,
    @Assisted("onDetailsClick") private val onDetailsClick: (Week, ContentType) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is HomeStore.Label.ClickOnDetails -> {
                        onDetailsClick(
                            it.week,
                            it.contentType
                        )
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

    override fun onClickDetails(week: Week, contentType: ContentType) {
        store.accept(HomeStore.Intent.ClickOnDetails(week, contentType))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onDetailsClick") onDetailsClick: (Week, ContentType) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultHomeComponent
    }
}