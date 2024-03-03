package com.margarin.commonpregnancy.presentation.main.home

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.home.details.DetailsComponent
import com.margarin.commonpregnancy.presentation.main.home.advices.AdvicesComponent

interface HomeComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Details(val component: DetailsComponent): Child

        data class Advices(val component: AdvicesComponent): Child
    }
}