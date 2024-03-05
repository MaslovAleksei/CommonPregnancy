package com.margarin.commonpregnancy.firstsetting

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.margarin.commonpregnancy.CONCEPTION_DURATION
import com.margarin.commonpregnancy.PREGNANCY_DURATION
import com.margarin.commonpregnancy.formattedFullDate
import com.margarin.commonpregnancy.toCalendar
import com.margarin.commonpregnancy.ui.theme.Pink
import com.margarin.settings.R
import java.util.Calendar

@Composable
fun FirstSettingContent(component: FirstSettingComponent) {

    val state by component.model.collectAsState()
    val currentState = state

    val lastMenstruationDate = currentState.timeStamp.toCalendar()
    val dateOfBirth = currentState.timeStamp.toCalendar().apply {
        add(Calendar.DAY_OF_YEAR, PREGNANCY_DURATION)
    }
    val dateOfConception = currentState.timeStamp.toCalendar().apply {
        add(Calendar.DAY_OF_YEAR, CONCEPTION_DURATION)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    modifier = Modifier.height(150.dp),
                    painter = painterResource(id = R.drawable.calendar_image),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.let_us_define_term),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.fill_any_3_fields),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextDateRow(
                text = stringResource(R.string.last_menstruation),
                date = if (currentState.timeStamp != 0L) {
                    lastMenstruationDate.formattedFullDate()
                } else {
                    stringResource(id = R.string.specify_date)
                },
                onConfirmDateClick = { lastMenstruation ->
                    component.onChangeTerm(timeStamp = lastMenstruation)
                }
            )
            TextDateRow(
                text = stringResource(R.string.date_of_conception),
                date = if (currentState.timeStamp != 0L) {
                    dateOfConception.formattedFullDate()
                } else {
                    stringResource(id = R.string.specify_date)
                },
                onConfirmDateClick = { dateOfConception ->
                    val lastMenstruation = dateOfConception.toCalendar().apply {
                        add(Calendar.DAY_OF_YEAR, -CONCEPTION_DURATION)
                    }.timeInMillis
                    component.onChangeTerm(timeStamp = lastMenstruation)
                }
            )
            TextDateRow(
                text = stringResource(R.string.date_of_birth),
                date = if (currentState.timeStamp != 0L) {
                    dateOfBirth.formattedFullDate()
                } else {
                    stringResource(id = R.string.specify_date)
                },
                onConfirmDateClick = { dateOfBirth ->
                    val lastMenstruation = dateOfBirth.toCalendar().apply {
                        add(Calendar.DAY_OF_YEAR, -PREGNANCY_DURATION)
                    }.timeInMillis
                    component.onChangeTerm(timeStamp = lastMenstruation)
                }
            )
        }

        Column(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isAgreed = currentState.isAgreed
                IconButton(
                    onClick = { component.onChangeAgreementCheckState() },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = if (isAgreed) {
                            Icons.Filled.CheckCircle
                        } else {
                            Icons.Outlined.Circle
                        },
                        tint = if (isAgreed) Pink else Color.White,
                        modifier = Modifier
                            .size(35.dp)
                            .background(
                                color = if (isAgreed) Color.White else Color.Transparent,
                                shape = CircleShape
                            )
                            .border(width = 2.dp, shape = CircleShape, color = Pink),
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(R.string.license_agreement),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { component.onSaveChanges() },
                enabled = currentState.isTermChanged && currentState.isAgreed,
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