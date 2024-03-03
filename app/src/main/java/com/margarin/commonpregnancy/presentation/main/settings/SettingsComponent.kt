package com.margarin.commonpregnancy.presentation.main.settings

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.settings.setting.SettingComponent
import com.margarin.commonpregnancy.presentation.main.settings.terms.TermsComponent

interface SettingsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Setting(val component: SettingComponent): Child

        data class Terms(val component: TermsComponent): Child
    }
}