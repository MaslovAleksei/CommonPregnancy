package com.margarin.commonpregnancy.firstsetting

import kotlinx.coroutines.flow.StateFlow

interface FirstSettingComponent {

    val model: StateFlow<FirstSettingStore.State>

    fun onSaveChanges()
    fun onChangeTerm(timeStamp: Long)
    fun onChangeAgreementCheckState()
}