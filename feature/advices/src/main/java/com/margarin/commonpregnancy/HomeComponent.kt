package com.margarin.commonpregnancy

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.details.DetailsComponent
import com.margarin.commonpregnancy.advices.AdvicesComponent

interface HomeComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Details(val component: DetailsComponent): Child

        data class Advices(val component: AdvicesComponent): Child
    }
}