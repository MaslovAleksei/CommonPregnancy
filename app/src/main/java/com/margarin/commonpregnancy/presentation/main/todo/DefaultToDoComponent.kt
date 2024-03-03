package com.margarin.commonpregnancy.presentation.main.todo

import com.arkivanov.decompose.ComponentContext

internal class DefaultToDoComponent(
    componentContext: ComponentContext,
) : ToDoComponent, ComponentContext by componentContext {


}