package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import javax.inject.Inject

class GetWeekUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(weekNumber: Int) = repository.getWeek(weekNumber)
}