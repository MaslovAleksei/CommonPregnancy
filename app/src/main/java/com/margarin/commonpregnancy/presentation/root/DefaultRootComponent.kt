package com.margarin.commonpregnancy.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.details.DefaultDetailsComponent
import com.margarin.commonpregnancy.presentation.home.DefaultHomeComponent
import com.margarin.commonpregnancy.presentation.utils.ContentType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val homeComponentFactory: DefaultHomeComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    week = config.week,
                    contentType = config.contentType,
                    onBackClicked = { navigation.pop() },
                    componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }

            Config.Home -> {
                val component = homeComponentFactory.create(
                    onDetailsClick = { week, contentType ->
                        navigation.push(
                            Config.Details(
                                week = week,
                                contentType = contentType
                            )
                        )
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Home(component)
            }
        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object Home : Config

        @Parcelize
        data class Details(val week: Week, val contentType: ContentType) : Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}