package com.margarin.commonpregnancy.presentation.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.details.DetailsComponent
import com.margarin.commonpregnancy.presentation.main.home.HomeComponent

interface MainComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Details(val component: DetailsComponent): Child

        data class Home(val component: HomeComponent): Child
    }
}