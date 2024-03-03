package com.margarin.commonpregnancy.domain.usecase

import com.margarin.commonpregnancy.domain.Repository
import javax.inject.Inject

class CheckIsConfiguredUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.checkIsConfigured()
}