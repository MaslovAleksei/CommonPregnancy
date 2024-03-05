package com.margarin.commonpregnancy.advices

import com.margarin.commonpregnancy.model.Week
import com.margarin.commonpregnancy.utils.ContentType
import kotlinx.coroutines.flow.StateFlow

interface AdvicesComponent {

    val model: StateFlow<AdvicesStore.State>

    fun changeWeek(weekNumber: Int)
    fun onClickDetails(week: Week, contentType: ContentType)
}