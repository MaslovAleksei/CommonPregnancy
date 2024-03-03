package com.margarin.commonpregnancy.presentation.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.presentation.firstsetting.FirstSettingContent
import com.margarin.commonpregnancy.presentation.main.MainContent
import com.margarin.commonpregnancy.presentation.ui.theme.CommonPregnancyTheme

@Composable
fun RootContent(
    modifier: Modifier,
    component: RootComponent
) {
    CommonPregnancyTheme {
        Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
            Children(
                stack = component.childStack,
                modifier = modifier
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.Main -> {
                        MainContent(component = instance.component)
                    }

                    is RootComponent.Child.FirstSetting -> {
                        FirstSettingContent(component = instance.component)
                    }
                }
            }
        }
    }
}