package com.margarin.commonpregnancy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.setting.SettingScreenContent
import com.margarin.commonpregnancy.terms.TermsContent

@Composable
fun SettingsContent(
    modifier: Modifier,
    component: SettingsComponent
) {
    Children(
        stack = component.childStack,
        modifier = modifier
    ) {
        when (val instance = it.instance) {
            is SettingsComponent.Child.Setting -> {
                SettingScreenContent(component = instance.component)
            }

            is SettingsComponent.Child.Terms -> {
                TermsContent(component = instance.component)
            }
        }
    }
}