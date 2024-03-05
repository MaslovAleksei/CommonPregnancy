package com.margarin.commonpregnancy.domain

import com.margarin.commonpregnancy.domain.model.Task
import com.margarin.commonpregnancy.domain.model.Term
import com.margarin.commonpregnancy.domain.model.Week
import kotlinx.coroutines.flow.Flow

interface Repository {

    /** Week */

    suspend fun getWeek(weekNumber: Int) : Week

    /** Term */

    fun getTimeOfStartPregnancy() : Flow<Term>

    suspend fun saveTimeOfStartPregnancy(timeInMillis: Long)

    suspend fun checkIsConfigured() : Boolean

    /** Task */

    suspend fun getTaskListUseCase(): List<Task>

    suspend fun checkIsTaskCompleted(value: String): Boolean

    suspend fun addToCompleted(task: Task)

    suspend fun removeFromCompleted(value: String)

}