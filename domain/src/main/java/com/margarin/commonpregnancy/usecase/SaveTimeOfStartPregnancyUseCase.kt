package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import javax.inject.Inject

class SaveTimeOfStartPregnancyUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(timeInMillis: Long) =
        repository.saveTimeOfStartPregnancy(timeInMillis)
}