package com.margarin.commonpregnancy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.HomeComponent.Child.Details
import com.margarin.commonpregnancy.HomeComponent.Child.Advices
import com.margarin.commonpregnancy.details.DetailsContent
import com.margarin.commonpregnancy.advices.AdvicesScreenContent

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