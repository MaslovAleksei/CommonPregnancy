package com.margarin.commonpregnancy.domain

import com.margarin.commonpregnancy.domain.model.Term
import com.margarin.commonpregnancy.domain.model.Week
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getWeek(weekNumber: Int) : Week

    fun getTimeOfStartPregnancy() : Flow<Term>

    suspend fun saveTimeOfStartPregnancy(timeInMillis: Long)
}