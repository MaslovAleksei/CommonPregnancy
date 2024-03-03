package com.margarin.commonpregnancy.presentation.main.home.advices

import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.utils.ContentType
import kotlinx.coroutines.flow.StateFlow

interface AdvicesComponent {

    val model: StateFlow<AdvicesStore.State>

    fun changeWeek(weekNumber: Int)
    fun onClickDetails(week: Week, contentType: ContentType)
}