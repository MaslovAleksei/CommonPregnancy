package com.margarin.commonpregnancy.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.presentation.details.DetailsContent
import com.margarin.commonpregnancy.presentation.home.HomeScreenContent
import com.margarin.commonpregnancy.presentation.ui.theme.CommonPregnancyTheme

@Composable
fun RootContent(component: DefaultRootComponent) {

    CommonPregnancyTheme {
        Children(stack = component.stack) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instance.component)
                }

                is RootComponent.Child.Home -> {
                    HomeScreenContent(component = instance.component)
                }
            }
        }
    }
}