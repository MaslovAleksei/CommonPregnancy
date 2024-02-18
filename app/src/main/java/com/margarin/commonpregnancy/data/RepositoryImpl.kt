package com.margarin.commonpregnancy.data

import android.content.Context
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.domain.Repository
import com.margarin.commonpregnancy.domain.model.Week
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val context: Context
) : Repository {
    override suspend fun getWeekList(): List<Week> {
        val list = mutableListOf<Week>()
        context.resources.getStringArray(R.array.weeks).forEachIndexed { index, string ->
            val week = Week(
                id = index + 1,
                text = string
            )
            list.add(week)
        }
        return list
    }
}