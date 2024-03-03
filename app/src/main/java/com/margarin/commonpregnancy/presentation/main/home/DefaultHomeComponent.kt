package com.margarin.commonpregnancy.presentation.main.home

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.main.home.details.DefaultDetailsComponent
import com.margarin.commonpregnancy.presentation.main.home.advices.DefaultAdvicesComponent
import com.margarin.commonpregnancy.presentation.utils.ContentType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultHomeComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val advicesComponentFactory: DefaultAdvicesComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, HomeComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Advices,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): HomeComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    week = config.week,
                    contentType = config.contentType,
                    onBackClicked = { navigation.pop() },
                    componentContext = componentContext
                )
                HomeComponent.Child.Details(component)
            }

            Config.Advices -> {
                val component = advicesComponentFactory.create(
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
                HomeComponent.Child.Advices(component)
            }
        }
    }

    sealed interface Config : Parcelable {

        @Parcelize
        data object Advices : Config

        @Parcelize
        data class Details(val week: Week, val contentType: ContentType) : Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultHomeComponent
    }
}