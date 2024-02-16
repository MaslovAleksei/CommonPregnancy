package com.margarin.commonpregnancy.domain

import com.margarin.commonpregnancy.domain.model.Week

interface Repository {

    suspend fun getWeekList() : List<Week>
}