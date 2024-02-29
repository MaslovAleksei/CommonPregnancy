package com.margarin.commonpregnancy.domain.usecase

import com.margarin.commonpregnancy.domain.Repository
import javax.inject.Inject

class GetWeekUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(weekNumber: Int) = repository.getWeek(weekNumber)
}