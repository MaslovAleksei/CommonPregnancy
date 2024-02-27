package com.margarin.commonpregnancy.presentation.main.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State>

    fun onClickBack()
}