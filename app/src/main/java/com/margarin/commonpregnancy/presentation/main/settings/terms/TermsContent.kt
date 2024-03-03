package com.margarin.commonpregnancy.presentation.main.settings.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.presentation.ui.theme.Pink
import com.margarin.commonpregnancy.presentation.utils.formattedFullDate
import com.margarin.commonpregnancy.presentation.utils.toCalendar
import java.util.Calendar

@Composable
fun TermsContent(component: TermsComponent) {

    val state by component.model.collectAsState()
    val currentState = state

    val lastMenstruationDate = currentState.timeStamp.toCalendar()
    val dateOfBirth = currentState.timeStamp.toCalendar().apply {
        add(Calendar.DAY_OF_YEAR, 280)
    }
    val dateOfConception = currentState.timeStamp.toCalendar().apply {
        add(Calendar.DAY_OF_YEAR, 14)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { component.onClickBack() }
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(width = 1.dp, color = Pink, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = Pink
                )
            }
            Text(
                text = stringResource(R.string.change_term),
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            TextDateRow(
                text = stringResource(R.string.last_menstruation),
                date = lastMenstruationDate.formattedFullDate(),
                onConfirmDateClick = { lastMenstruation ->
                    component.onChangeTerm(timeStamp = lastMenstruation)
                }
            )
            TextDateRow(
                text = stringResource(R.string.date_of_conception),
                date = dateOfConception.formattedFullDate(),
                onConfirmDateClick = { dateOfConception ->
                    val lastMenstruation = dateOfConception.toCalendar().apply {
                        add(Calendar.DAY_OF_YEAR, -14)
                    }.timeInMillis
                    component.onChangeTerm(timeStamp = lastMenstruation)
                }
            )
            TextDateRow(
                text = stringResource(R.string.date_of_birth),
                date = dateOfBirth.formattedFullDate(),
                onConfirmDateClick = { dateOfBirth ->
                    val lastMenstruation = dateOfBirth.toCalendar().apply {
                        add(Calendar.DAY_OF_YEAR, -280)
                    }.timeInMillis
                    component.onChangeTerm(timeStamp = lastMenstruation)
                }
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { component.onSaveChanges(currentState.timeStamp) },
            enabled = currentState.isTermChanged,
            colors = ButtonColors(
                containerColor = Pink,
                contentColor = Color.White,
                disabledContainerColor = Pink.copy(alpha = 0.3f),
                disabledContentColor = Color.White.copy(alpha = 0.8f)
            ),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.save))
                Icon(imageVector = Icons.Outlined.ArrowCircleRight, contentDescription = null)
            }
        }
    }
}

@Composable
private fun TextDateRow(
    text: String,
    date: String,
    onConfirmDateClick: (Long) -> Unit
) {
    var datePickerDialogState by remember {
        mutableStateOf(false)
    }

    if (datePickerDialogState) {
        DatePickerDialog(
            onConfirmDateClick = onConfirmDateClick,
            setFalseDatePickerState = {
                datePickerDialogState = false
            }
        )
    }
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
    Row(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { datePickerDialogState = true }
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.bodySmall
        )
        Icon(
            imageVector = Icons.Outlined.CalendarToday,
            contentDescription = null,
            tint = Pink
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    onConfirmDateClick: (Long) -> Unit,
    setFalseDatePickerState: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        onDismissRequest = { setFalseDatePickerState() },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onConfirmDateClick(it)
                    }
                    setFalseDatePickerState()
                },
                enabled = confirmEnabled.value
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    setFalseDatePickerState()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        colors = DatePickerDefaults.colors(containerColor = Color.White),
        tonalElevation = 0.dp
    ) {
        DatePicker(state = datePickerState)
    }
}