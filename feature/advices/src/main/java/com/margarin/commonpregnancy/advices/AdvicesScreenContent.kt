package com.margarin.commonpregnancy.advices

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.margarin.advices.R
import com.margarin.commonpregnancy.PREGNANCY_DURATION
import com.margarin.commonpregnancy.formattedDayMonth
import com.margarin.commonpregnancy.model.Term
import com.margarin.commonpregnancy.toCalendar
import com.margarin.commonpregnancy.ui.theme.Blue
import com.margarin.commonpregnancy.ui.theme.Green
import com.margarin.commonpregnancy.ui.theme.Pink
import com.margarin.commonpregnancy.ui.theme.Purple
import com.margarin.commonpregnancy.utils.ContentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvicesScreenContent(component: AdvicesComponent) {

    val state by component.model.collectAsState()
    val currentWeek = state.week
    val term = state.term
    val currentDate = Calendar.getInstance(Locale.getDefault())
    val daysOfYearPassed = if (term.timeOfStartPregnancy.timeInMillis != 0L) {
        (currentDate.timeInMillis - term.timeOfStartPregnancy.timeInMillis).toCalendar()
            .get(Calendar.DAY_OF_YEAR) - 1
    } else 0
    val weeksOfYearPassed = daysOfYearPassed / 7

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(currentWeek.color), Color.White),
                    start = Offset.Zero,
                    end = Offset(
                        x = 0f,
                        y = LocalConfiguration.current.screenHeightDp.toFloat()
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentWeek.weight.isNotEmpty()) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = currentWeek.weight + " " + stringResource(R.string.gram),
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = stringResource(R.string.weight),
                        color = MaterialTheme.colorScheme.background
                    )
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = currentWeek.length + " " + stringResource(R.string.centimetre),
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = stringResource(R.string.length),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = currentWeek.childImageResId),
            contentDescription = null,
            modifier = Modifier.height(200.dp),
            contentScale = ContentScale.FillHeight,
        )
        Image(
            painter = painterResource(R.drawable.shadow),
            contentDescription = null,
            modifier = Modifier
                .height(10.dp)
                .width(150.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.1f
        )

        val pagerState = rememberPagerState { 40 }
        val fling = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(40)
        )
        LaunchedEffect(key1 = term) {
            snapshotFlow { weeksOfYearPassed }.collect {
                if (it in 0..39) {
                    pagerState.scrollToPage(it)
                }
            }
        }
        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                component.changeWeek(it)
            }
        }
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            pageSize = PageSize.Fixed(ITEM_DIAMETER.dp),
            flingBehavior = fling,
            contentPadding = PaddingValues(
                vertical = 24.dp,
                horizontal = ((LocalConfiguration.current.screenWidthDp - ITEM_DIAMETER) / 2).dp
            )
        ) { weekNumber ->
            WeekNumberItem(
                number = weekNumber,
                pagerState = pagerState,
                scope = rememberCoroutineScope(),
                color = Color(currentWeek.color),
                currentWeek = weeksOfYearPassed
            )
        }

        var offset = 0
        if (currentWeek.id == weeksOfYearPassed) {
            ProgressField(
                modifier = Modifier,
                term = term,
                daysOfYearPassed = daysOfYearPassed,
                weeksOfYearPassed = weeksOfYearPassed
            )
            offset = -40
        }
        if (currentWeek.childDetails.isNotEmpty()) {
            TextField(
                modifier = Modifier.offset(x = 0.dp, y = offset.dp),
                title = stringResource(R.string.baby),
                text = currentWeek.childDetails,
                containerColor = Pink,
                onDetailsCardClick = {
                    component.onClickDetails(
                        week = currentWeek,
                        contentType = ContentType.ChildDetails
                    )
                }
            )
        }
        if (currentWeek.motherDetails.isNotEmpty()) {
            TextField(
                modifier = Modifier.offset(x = 0.dp, y = (offset - 40).dp),
                title = stringResource(R.string.mother),
                text = currentWeek.motherDetails,
                containerColor = Purple,
                onDetailsCardClick = {
                    component.onClickDetails(
                        week = currentWeek,
                        contentType = ContentType.MotherDetails
                    )
                }
            )
        }
        if (currentWeek.adviceDetails.isNotEmpty()) {
            TextField(
                modifier = Modifier.offset(x = 0.dp, y = (offset - 80).dp),
                title = stringResource(R.string.advices),
                text = currentWeek.adviceDetails,
                containerColor = Green,
                onDetailsCardClick = {
                    component.onClickDetails(
                        week = currentWeek,
                        contentType = ContentType.AdviceDetails
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
private fun WeekNumberItem(
    number: Int,
    pagerState: PagerState,
    scope: CoroutineScope,
    color: Color,
    currentWeek: Int
) {

    var weekLabel = ""
    var backgroundColor = color.copy(alpha = 0.05f)
    var textColor = color
    var strokeWidth = 1.dp

    if (number == pagerState.currentPage) {
        weekLabel = stringResource(R.string.week)
        backgroundColor = color
        textColor = Color.White

        if (number == currentWeek) {
            weekLabel = stringResource(R.string.current_week)
        }
    } else if (number == currentWeek) {
        weekLabel = stringResource(R.string.current_week)
        backgroundColor = color
        textColor = Color.White
        strokeWidth = 4.dp
    }
    Column(
        modifier = Modifier
            .height(110.dp)
            .graphicsLayer {
                val pageOffset = (
                        (pagerState.currentPage - number) + pagerState
                            .currentPageOffsetFraction
                        )

                val size = if (pageOffset in -0.1f..0.1f) 1f else 0.85f
                scaleX = size
                scaleY = size
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = ITEM_DIAMETER.dp, height = ITEM_DIAMETER.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    scope.launch {
                        pagerState.animateScrollToPage(page = number)
                    }
                }
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(backgroundColor.copy(alpha = 0.05f), backgroundColor)
                    ),
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(
                    width = strokeWidth,
                    color = color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (number + 1).toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = textColor
            )
        }
        Text(
            text = weekLabel,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TextField(
    modifier: Modifier,
    title: String,
    text: String,
    containerColor: Color,
    onDetailsCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onDetailsCardClick() }
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = Color.White
        )

    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = containerColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                maxLines = 5,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ProgressField(
    modifier: Modifier,
    term: Term,
    daysOfYearPassed: Int,
    weeksOfYearPassed: Int
) {

    val daysOfWeekPassed = daysOfYearPassed % 7
    val currentTrimester = when (weeksOfYearPassed) {
        in 0..13 -> 1
        in 14..26 -> 2
        in 27..42 -> 3
        else -> 0
    }.toString()
    val dateOfBirth = (term.timeOfStartPregnancy.timeInMillis +
            PREGNANCY_DURATION.toLong() * 24 * 60 * 60 * 1000).toCalendar()
    val weeksOfYearLeft = (PREGNANCY_DURATION - daysOfYearPassed) / 7
    val daysOfWeekLeft = (PREGNANCY_DURATION - daysOfYearPassed) % 7

    val stringTimePassed = stringResource(id = R.string.weeks_passed) + " " + weeksOfYearPassed +
            ", " + stringResource(id = R.string.days) + " " + daysOfWeekPassed
    val stringTrimester = currentTrimester + " " + stringResource(R.string.trimester)
    val stringDateOfBirth = stringResource(id = R.string.date_of_birth) + ": " +
            dateOfBirth.formattedDayMonth()
    val stringTimeLeft = stringResource(id = R.string.weeks_left) + " " + weeksOfYearLeft + ", " +
            stringResource(id = R.string.days) + " " + daysOfWeekLeft

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue,
            contentColor = Color.White
        )

    ) {
        Column(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringTrimester,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringTimePassed,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(8.dp),
                progress = { daysOfYearPassed.toFloat() / PREGNANCY_DURATION.toFloat() },
                color = Color.White,
                strokeCap = StrokeCap.Round,
                trackColor = Color.White.copy(alpha = 0.3f),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringDateOfBirth,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringTimeLeft,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private const val ITEM_DIAMETER = 70