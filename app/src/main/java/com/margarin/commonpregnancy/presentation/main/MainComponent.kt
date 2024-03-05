package com.margarin.commonpregnancy.presentation.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.HomeComponent
import com.margarin.commonpregnancy.SettingsComponent
import com.margarin.commonpregnancy.TaskListComponent

interface MainComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onHomeTabClicked()
    fun onTaskTabClicked()
    fun onSettingsTabClicked()

    sealed class Child {

        class HomeChild(val component: HomeComponent) : Child()
        class TaskChild(val component: TaskListComponent) : Child()
        class SettingsChild(val component: SettingsComponent) : Child()
    }
}