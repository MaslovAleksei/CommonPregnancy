package com.margarin.commonpregnancy.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.Details
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.Home
import com.margarin.commonpregnancy.presentation.main.details.DetailsContent
import com.margarin.commonpregnancy.presentation.main.home.HomeScreenContent

@Composable
fun MainContent(
    modifier: Modifier,
    component: MainComponent
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
    ) {
        when (val instance = it.instance) {
            is Details -> {
                DetailsContent(component = instance.component)
            }

            is Home -> {
                HomeScreenContent(component = instance.component)
            }
        }
    }
}