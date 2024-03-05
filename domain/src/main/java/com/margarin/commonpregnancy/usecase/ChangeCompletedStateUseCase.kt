package com.margarin.commonpregnancy.usecase

import com.margarin.commonpregnancy.Repository
import com.margarin.commonpregnancy.model.Task
import javax.inject.Inject

class ChangeCompletedStateUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun addToCompleted(task: Task) = repository.addToCompleted(task)

    suspend fun removeFromCompleted(value: String) = repository.removeFromCompleted(value)
}