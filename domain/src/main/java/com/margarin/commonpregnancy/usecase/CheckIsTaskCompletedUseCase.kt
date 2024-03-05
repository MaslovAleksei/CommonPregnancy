package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import javax.inject.Inject

class CheckIsTaskCompletedUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(value: String) = repository.checkIsTaskCompleted(value)
}