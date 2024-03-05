package com.margarin.commonpregnancy.setting

import kotlinx.coroutines.flow.StateFlow

interface SettingComponent {

    val model: StateFlow<SettingStore.State>

    fun onClickTerms()
}