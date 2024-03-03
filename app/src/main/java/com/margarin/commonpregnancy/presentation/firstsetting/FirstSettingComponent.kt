package com.margarin.commonpregnancy.presentation.firstsetting

import kotlinx.coroutines.flow.StateFlow

interface FirstSettingComponent {

    val model: StateFlow<FirstSettingStore.State>

    fun onSaveChanges(timeStamp: Long)
    fun onChangeTerm(timeStamp: Long)
    fun onChangeAgreementCheckState()
}