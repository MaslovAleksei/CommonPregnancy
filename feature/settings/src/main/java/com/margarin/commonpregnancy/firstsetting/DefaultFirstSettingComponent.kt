package com.margarin.commonpregnancy.firstsetting

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.margarin.commonpregnancy.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultFirstSettingComponent @AssistedInject constructor(
    private val storeFactory: FirstSettingStoreFactory,
    @Assisted("onSaveChangesClicked") private val onSaveChangesClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : FirstSettingComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    FirstSettingStore.Label.SaveChanges -> {
                        onSaveChangesClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FirstSettingStore.State> = store.stateFlow

    override fun onSaveChanges() {
        store.accept(FirstSettingStore.Intent.SaveChanges)
    }

    override fun onChangeTerm(timeStamp: Long) {
        store.accept(FirstSettingStore.Intent.ChangeTerm(timeStamp))
    }

    override fun onChangeAgreementCheckState() {
        store.accept(FirstSettingStore.Intent.ChangeAgreementCheckState)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onSaveChangesClicked") onSaveChangesClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultFirstSettingComponent
    }
}