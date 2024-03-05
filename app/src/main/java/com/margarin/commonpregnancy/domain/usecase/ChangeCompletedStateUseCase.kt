package com.margarin.commonpregnancy.domain.usecase

import com.margarin.commonpregnancy.domain.Repository
import com.margarin.commonpregnancy.domain.model.Task
import javax.inject.Inject

class ChangeCompletedStateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun addToCompleted(task: Task) = repository.addToCompleted(task)

    suspend fun removeFromCompleted(value: String) = repository.removeFromCompleted(value)
}