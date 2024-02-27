package com.margarin.commonpregnancy.presentation.main.home

import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.utils.ContentType
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val model: StateFlow<HomeStore.State>

    fun changeWeek(weekNumber: Int)
    fun onClickDetails(week: Week, contentType: ContentType)
}