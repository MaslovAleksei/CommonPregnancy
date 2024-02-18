package com.margarin.commonpregnancy.presentation.home

import com.margarin.commonpregnancy.domain.model.Week
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val model: StateFlow<HomeStore.State>

    fun changeWeek(weekNumber: Int)
    fun onClickOnMotherDetails(week: Week)
    fun onClickOnChildDetails(week: Week)
    fun onClickOnAdvice(week: Week)
}