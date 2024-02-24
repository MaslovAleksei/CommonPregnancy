package com.margarin.commonpregnancy.data

import android.content.Context
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.domain.Repository
import com.margarin.commonpregnancy.domain.model.Week
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val context: Context
) : Repository {

    override suspend fun getWeek(weekNumber: Int): Week {
        val childImageResId = weekNumber.getChildImageResId()
        val weight = context.resources.getStringArray(R.array.weight_by_weeks)[weekNumber]
        val length = context.resources.getStringArray(R.array.length_by_weeks)[weekNumber]
        val motherDetails = context.resources.getStringArray(R.array.mother_by_weeks)[weekNumber]
        val childDetails = context.resources.getStringArray(R.array.child_by_weeks)[weekNumber]
        return Week(
            weight = weight,
            length = length,
            motherDetails = motherDetails,
            childDetails = childDetails,
            childImageResId = childImageResId
        )
    }
}