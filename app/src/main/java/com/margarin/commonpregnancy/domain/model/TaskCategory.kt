package com.margarin.commonpregnancy.domain.model

sealed class TaskCategory {

    data object FirstTrimester: TaskCategory()
    data object SecondTrimester: TaskCategory()
    data object ThirdTrimester: TaskCategory()
    data object ThingsForHospital: TaskCategory()
    data object ThingsAfterBirth: TaskCategory()
    data object ThingsForDischarge: TaskCategory()
}