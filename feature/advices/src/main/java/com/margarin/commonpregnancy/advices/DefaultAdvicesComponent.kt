package com.margarin.commonpregnancy.advices

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.margarin.commonpregnancy.componentScope
import com.margarin.commonpregnancy.model.Week
import com.margarin.commonpregnancy.utils.ContentType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultAdvicesComponent @AssistedInject constructor(
    private val storeFactory: AdvicesStoreFactory,
    @Assisted("onDetailsClick") private val onDetailsClick: (Week, ContentType) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : AdvicesComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is AdvicesStore.Label.ClickOnDetails -> {
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
    override val model: StateFlow<AdvicesStore.State> = store.stateFlow

    override fun changeWeek(weekNumber: Int) {
        store.accept(AdvicesStore.Intent.ChangeWeek(weekNumber))
    }

    override fun onClickDetails(week: Week, contentType: ContentType) {
        store.accept(AdvicesStore.Intent.ClickOnDetails(week, contentType))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onDetailsClick") onDetailsClick: (Week, ContentType) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultAdvicesComponent
    }
}