package com.margarin.commonpregnancy.domain

import com.margarin.commonpregnancy.domain.model.Term
import com.margarin.commonpregnancy.domain.model.Week

interface Repository {

    suspend fun getWeek(weekNumber: Int) : Week

    suspend fun getTimeOfStartPregnancy() : Term?

    suspend fun saveTimeOfStartPregnancy(timeInMillis: Long)
}