package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import javax.inject.Inject

class GetTimeOfStartPregnancyUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getTimeOfStartPregnancy()
}