package com.margarin.commonpregnancy.data

import com.margarin.commonpregnancy.domain.Repository
import com.margarin.commonpregnancy.domain.model.Week

class RepositoryImpl(

) : Repository {
    override suspend fun getWeekList(): List<Week> {
        val list = mutableListOf<Week>()

        return mutableListOf()
    }
}