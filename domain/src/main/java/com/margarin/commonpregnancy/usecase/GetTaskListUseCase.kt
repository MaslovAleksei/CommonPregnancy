package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getTaskListUseCase()
}