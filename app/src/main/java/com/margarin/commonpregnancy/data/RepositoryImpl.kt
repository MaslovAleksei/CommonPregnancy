package com.margarin.commonpregnancy.data

import android.content.Context
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.data.local.db.TaskDao
import com.margarin.commonpregnancy.data.local.db.TermDao
import com.margarin.commonpregnancy.domain.Repository
import com.margarin.commonpregnancy.domain.model.Task
import com.margarin.commonpregnancy.domain.model.TaskCategory
import com.margarin.commonpregnancy.domain.model.Term
import com.margarin.commonpregnancy.domain.model.Week
import com.margarin.commonpregnancy.presentation.ui.theme.colorList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val context: Context,
    private val termDao: TermDao,
    private val taskDao: TaskDao,
) : Repository {

    /** Week */
    override suspend fun getWeek(weekNumber: Int) = Week(
        id = weekNumber,
        weight = context.resources.getStringArray(R.array.weight_by_weeks)[weekNumber],
        length = context.resources.getStringArray(R.array.length_by_weeks)[weekNumber],
        motherDetails = context.resources.getStringArray(R.array.mother_by_weeks)[weekNumber],
        childDetails = context.resources.getStringArray(R.array.child_by_weeks)[weekNumber],
        adviceDetails = context.resources.getStringArray(R.array.advices_by_weeks)[weekNumber],
        childImageResId = weekNumber.getChildImageResId(),
        motherImageResId = weekNumber.getMotherImageResId(),
        color = colorList[weekNumber]
    )

    /** Term */
    override fun getTimeOfStartPregnancy(): Flow<Term> {
        return termDao.getTimeOfStartPregnancy().map { it?.toEntity() ?: Term() }
    }

    override suspend fun saveTimeOfStartPregnancy(timeInMillis: Long) {
        termDao.saveTimeOfStartPregnancy(timeInMillis.toTermDbModel())
    }

    override suspend fun checkIsConfigured(): Boolean = termDao.checkIsConfigured()


    /** Task */
    override suspend fun getTaskListUseCase(): List<Task> {
        val list = mutableListOf<Task>()
        context.resources.getStringArray(R.array.tasks_first_trimester).forEach {
            list.add(
                Task(
                    value = it,
                    category = TaskCategory.FirstTrimester
                )
            )
        }
        context.resources.getStringArray(R.array.tasks_second_trimester).forEach {
            list.add(
                Task(
                    value = it,
                    category = TaskCategory.SecondTrimester
                )
            )
        }
        context.resources.getStringArray(R.array.tasks_third_trimester).forEach {
            list.add(
                Task(
                    value = it,
                    category = TaskCategory.ThirdTrimester
                )
            )
        }
        context.resources.getStringArray(R.array.things_for_maternity_hospital).forEach {
            list.add(
                Task(
                    value = it,
                    category = TaskCategory.ThingsForHospital
                )
            )
        }
        context.resources.getStringArray(R.array.things_after_birth).forEach {
            list.add(
                Task(
                    value = it,
                    category = TaskCategory.ThingsAfterBirth
                )
            )
        }
        context.resources.getStringArray(R.array.things_for_discharge).forEach {
            list.add(
                Task(
                    value = it,
                    category = TaskCategory.ThingsForDischarge
                )
            )
        }
        return list
    }

    override suspend fun checkIsTaskCompleted(value: String) = taskDao.checkIsCompleted(value)

    override suspend fun addToCompleted(task: Task) {
        taskDao.addToCompleted(task.toTaskDbModel())
    }

    override suspend fun removeFromCompleted(value: String) {
        taskDao.removeFromCompleted(value)
    }
}