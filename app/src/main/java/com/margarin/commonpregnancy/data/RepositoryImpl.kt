package com.margarin.commonpregnancy.data

import android.content.Context
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.data.local.db.PregnancyDao
import com.margarin.commonpregnancy.domain.Repository
import com.margarin.commonpregnancy.domain.model.Term
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.ui.theme.colorList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val context: Context,
    private val pregnancyDao: PregnancyDao
) : Repository {

    override suspend fun getWeek(weekNumber: Int) = Week(
        id = weekNumber,
        weight = context.resources.getStringArray(R.array.weight_by_weeks)[weekNumber],
        length = context.resources.getStringArray(R.array.length_by_weeks)[weekNumber],
        motherDetails = context.resources.getStringArray(R.array.mother_by_weeks)[weekNumber],
        childDetails = context.resources.getStringArray(R.array.child_by_weeks)[weekNumber],
        adviceDetails = context.resources.getStringArray(R.array.advices_by_weeks)[weekNumber],
        childImageResId = weekNumber.getChildImageResId(),
        motherImageResId = weekNumber.getMotherImageResId(),
        color = colorList[weekNumber]
    )

    override fun getTimeOfStartPregnancy(): Flow<Term> {
        return pregnancyDao.getTimeOfStartPregnancy().map { it?.toEntity() ?: Term() }
    }

    override suspend fun saveTimeOfStartPregnancy(timeInMillis: Long) {
        pregnancyDao.saveTimeOfStartPregnancy(timeInMillis.toTermDbModel())
    }
}