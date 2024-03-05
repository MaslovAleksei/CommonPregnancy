package com.margarin.commonpregnancy.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.usecase.CheckIsConfiguredUseCase
import com.margarin.commonpregnancy.firstsetting.DefaultFirstSettingComponent
import com.margarin.commonpregnancy.presentation.main.DefaultMainComponent
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.*
import com.margarin.commonpregnancy.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalDecomposeApi::class)
class DefaultRootComponent @AssistedInject constructor(
    private val mainComponentFactory: DefaultMainComponent.Factory,
    private val firstSettingComponentFactory: DefaultFirstSettingComponent.Factory,
    private val checkIsConfiguredUseCase: CheckIsConfiguredUseCase,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()
    private val scope = componentScope()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::child
    )

    init {
        scope.launch {
            if (!checkIsConfiguredUseCase()) {
                navigation.push(
                    Config.FirstSetting
                )
            }
        }
    }

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.FirstSetting -> {
                val component = firstSettingComponentFactory.create(
                    componentContext = componentContext,
                    onSaveChangesClicked = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.FirstSetting(component)
            }

            Config.Main -> {
                val component = mainComponentFactory.create(
                    componentContext = componentContext
                )
                RootComponent.Child.Main(component)
            }

        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object Main : Config

        @Parcelize
        data object FirstSetting : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}