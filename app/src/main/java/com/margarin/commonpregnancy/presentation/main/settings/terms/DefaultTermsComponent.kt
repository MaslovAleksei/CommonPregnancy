package com.margarin.commonpregnancy.presentation.main.settings.terms

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

class DefaultTermsComponent @AssistedInject constructor(
    private val storeFactory: TermsStoreFactory,
    @Assisted("onSaveChangesClicked") private val onSaveChangesClicked: () -> Unit,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : TermsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    TermsStore.Label.ClickBack -> {
                        onBackClicked()
                    }

                    TermsStore.Label.SaveChanges -> {
                        onSaveChangesClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<TermsStore.State> = store.stateFlow

    override fun onSaveChanges() {
        store.accept(TermsStore.Intent.SaveChanges)
    }

    override fun onChangeTerm(timeStamp: Long) {
        store.accept(TermsStore.Intent.ChangeTerm(timeStamp))
    }

    override fun onClickBack() {
        store.accept(TermsStore.Intent.ClickBack)
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onSaveChangesClicked") onSaveChangesClicked: () -> Unit,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultTermsComponent
    }
}