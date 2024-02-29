package com.margarin.commonpregnancy.presentation.settings.setting

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.margarin.commonpregnancy.presentation.utils.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSettingComponent @AssistedInject constructor(
    private val storeFactory: SettingStoreFactory,
    @Assisted("onTermsClick") private val onTermsClick: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : SettingComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is SettingStore.Label.ClickOnTerms -> {
                        onTermsClick()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SettingStore.State> = store.stateFlow

    override fun onClickTerms() {
        store.accept(SettingStore.Intent.ClickOnTerms)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onTermsClick") onTermsClick: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultSettingComponent
    }
}