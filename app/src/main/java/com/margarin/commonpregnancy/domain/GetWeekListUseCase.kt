package com.margarin.commonpregnancy.domain

import javax.inject.Inject

class GetWeekListUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getWeekList()
}