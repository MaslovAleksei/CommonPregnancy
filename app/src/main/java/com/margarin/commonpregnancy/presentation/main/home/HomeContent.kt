package com.margarin.commonpregnancy.presentation.main.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.presentation.main.home.HomeComponent.Child.Details
import com.margarin.commonpregnancy.presentation.main.home.HomeComponent.Child.Advices
import com.margarin.commonpregnancy.presentation.main.home.details.DetailsContent
import com.margarin.commonpregnancy.presentation.main.home.advices.AdvicesScreenContent

@Composable
fun HomeContent(
    modifier: Modifier,
    component: HomeComponent
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
    ) {
        when (val instance = it.instance) {
            is Details -> {
                DetailsContent(component = instance.component)
            }

            is Advices -> {
                AdvicesScreenContent(component = instance.component)
            }
        }
    }
}