package com.margarin.commonpregnancy.presentation.main.settings.terms

import kotlinx.coroutines.flow.StateFlow

interface TermsComponent {

    val model: StateFlow<TermsStore.State>

    fun onSaveChanges(timeStamp: Long)
    fun onChangeTerm(timeStamp: Long)
    fun onClickBack()
}