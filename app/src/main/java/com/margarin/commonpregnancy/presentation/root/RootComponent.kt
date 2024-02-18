package com.margarin.commonpregnancy.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.details.DetailsComponent
import com.margarin.commonpregnancy.presentation.home.HomeComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Details(val component: DetailsComponent): Child

        data class Home(val component: HomeComponent): Child
    }
}