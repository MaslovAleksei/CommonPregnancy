package com.margarin.commonpregnancy.terms

import kotlinx.coroutines.flow.StateFlow

interface TermsComponent {

    val model: StateFlow<TermsStore.State>

    fun onSaveChanges()
    fun onChangeTerm(timeStamp: Long)
    fun onClickBack()
}