package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import javax.inject.Inject

class CheckIsConfiguredUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.checkIsConfigured()
}