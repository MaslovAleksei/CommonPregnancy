package com.margarin.commonpregnancy.presentation.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.home.HomeComponent
import com.margarin.commonpregnancy.presentation.main.settings.SettingsComponent
import com.margarin.commonpregnancy.presentation.main.todo.ToDoComponent

interface MainComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onHomeTabClicked()
    fun onToDoTabClicked()
    fun onSettingsTabClicked()

    sealed class Child {

        class HomeChild(val component: HomeComponent) : Child()
        class ToDoChild(val component: ToDoComponent) : Child()
        class SettingsChild(val component: SettingsComponent) : Child()
    }
}