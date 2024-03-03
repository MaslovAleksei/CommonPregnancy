package com.margarin.commonpregnancy.domain.model

import com.margarin.commonpregnancy.presentation.utils.toCalendar
import java.util.Calendar

data class Term(
    val timeOfStartPregnancy: Calendar = 0L.toCalendar()
    )