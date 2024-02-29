package com.margarin.commonpregnancy.presentation.settings

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.settings.setting.DefaultSettingComponent
import com.margarin.commonpregnancy.presentation.settings.terms.DefaultTermsComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize


class DefaultSettingsComponent@AssistedInject constructor(
    private val settingComponentFactory: DefaultSettingComponent.Factory,
    private val termsComponentFactory: DefaultTermsComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : SettingsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, SettingsComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Setting,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): SettingsComponent.Child {
        return when (config) {
            is Config.Setting -> {
                val component = settingComponentFactory.create(
                    onTermsClick = {
                        navigation.push(
                            Config.Terms
                        )
                    },
                    componentContext = componentContext
                )
                SettingsComponent.Child.Setting(component)
            }

            Config.Terms -> {
                val component = termsComponentFactory.create(
                    onSaveChangesClicked = { navigation.pop() },
                    onBackClicked = { navigation.pop() },
                    componentContext = componentContext
                )
                SettingsComponent.Child.Terms(component)
            }
        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object Setting : Config

        @Parcelize
        data object Terms : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultSettingsComponent
    }
}