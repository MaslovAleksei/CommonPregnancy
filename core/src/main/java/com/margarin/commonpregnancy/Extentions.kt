package com.margarin.commonpregnancy

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun ComponentContext.componentScope() =
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()).apply {
        lifecycle.doOnDestroy { cancel() }
    }

fun Long.toCalendar(): Calendar = Calendar.getInstance().apply {
    time = Date(this@toCalendar)
}

fun Calendar.formattedFullDate() : String {
    val format = SimpleDateFormat("d MMM y", Locale.getDefault())
    return format.format(time)
}

fun Calendar.formattedDayMonth() : String {
    val format = SimpleDateFormat("dd MMMM", Locale.getDefault())
    return format.format(time)
}
