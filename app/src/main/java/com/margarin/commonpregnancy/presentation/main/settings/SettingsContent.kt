package com.margarin.commonpregnancy.presentation.main.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.margarin.commonpregnancy.presentation.main.settings.setting.SettingScreenContent
import com.margarin.commonpregnancy.presentation.main.settings.terms.TermsContent

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