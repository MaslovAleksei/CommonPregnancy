package com.margarin.commonpregnancy.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.MainComponent
import com.margarin.commonpregnancy.presentation.settings.SettingsComponent
import com.margarin.commonpregnancy.presentation.todo.ToDoComponent

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onMainTabClicked()
    fun onToDoTabClicked()
    fun onSettingsTabClicked()

    sealed class Child {

        class MainChild(val component: MainComponent) : Child()
        class ToDoChild(val component: ToDoComponent) : Child()
        class SettingsChild(val component: SettingsComponent) : Child()
    }
}