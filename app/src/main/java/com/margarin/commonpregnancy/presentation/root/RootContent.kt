package com.margarin.commonpregnancy.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.presentation.ui.theme.CommonPregnancyTheme

@Composable
fun RootContent(component: DefaultRootComponent) {

    CommonPregnancyTheme {
        Children(stack = component.stack) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> {

                }

                is RootComponent.Child.Home -> {

                }
            }
        }
    }
}