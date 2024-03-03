package com.margarin.commonpregnancy.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.firstsetting.FirstSettingComponent
import com.margarin.commonpregnancy.presentation.main.MainComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class FirstSetting(val component: FirstSettingComponent): Child

        data class Main(val component: MainComponent): Child
    }
}