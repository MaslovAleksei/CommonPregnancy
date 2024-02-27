package com.margarin.commonpregnancy.presentation.main.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.presentation.ui.theme.Green
import com.margarin.commonpregnancy.presentation.ui.theme.Pink
import com.margarin.commonpregnancy.presentation.ui.theme.Purple
import com.margarin.commonpregnancy.presentation.utils.ContentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(component: HomeComponent) {

    val state by component.model.collectAsState()
    val currentState = state.week

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(currentState.color), Color.White),
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
            if (currentState.weight.isNotEmpty()) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = currentState.weight + " " + stringResource(R.string.gram),
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
                        text = currentState.length + " " + stringResource(R.string.santimeter),
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
            painter = painterResource(id = currentState.childImageResId),
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
        val lazyListState = rememberLazyListState()
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
            contentPadding = PaddingValues(
                top = 24.dp,
                bottom = 24.dp,
                start = (((LocalConfiguration.current.screenWidthDp - ITEM_DIAMETER) / 2).dp),
                end = (((LocalConfiguration.current.screenWidthDp - ITEM_DIAMETER) / 2).dp)
            )
        ) {
            items(40) { number ->
                WeekNumberItem(
                    number = number + 1,
                    setOnWeekChange = { component.changeWeek(number) },
                    state = lazyListState,
                    scope = rememberCoroutineScope(),
                    color = Color(currentState.color)
                )
            }
            item {
                Box(modifier = Modifier.width(20.dp))
            }
        }
        if (currentState.childDetails.isNotEmpty()) {
            TextField(
                modifier = Modifier,
                title = stringResource(R.string.baby),
                text = currentState.childDetails,
                containerColor = Pink,
                onDetailsCardClick = {
                    component.onClickDetails(
                        week = currentState,
                        contentType = ContentType.ChildDetails
                    )
                }
            )
        }
        if (currentState.motherDetails.isNotEmpty()) {
            TextField(
                modifier = Modifier.offset(x = 0.dp, y = (-40).dp),
                title = stringResource(R.string.mother),
                text = currentState.motherDetails,
                containerColor = Purple,
                onDetailsCardClick = {
                    component.onClickDetails(
                        week = currentState,
                        contentType = ContentType.MotherDetails
                    )
                }
            )
        }
        if (currentState.adviceDetails.isNotEmpty()) {
            TextField(
                modifier = Modifier.offset(x = 0.dp, y = (-80).dp),
                title = stringResource(R.string.advices),
                text = currentState.adviceDetails,
                containerColor = Green,
                onDetailsCardClick = {
                    component.onClickDetails(
                        week = currentState,
                        contentType = ContentType.AdviceDetails
                    )
                }
            )
        }
    }
}

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
private fun WeekNumberItem(
    number: Int,
    setOnWeekChange: (Int) -> Unit,
    state: LazyListState,
    scope: CoroutineScope,
    color: Color
) {

    val itemInfo = state.layoutInfo.visibleItemsInfo.firstOrNull { it.index == number }
    var weekLabel = ""
    var backgroundColor = Color.White
    var textColor = color

    itemInfo?.let {
        val delta = it.size / 1.8
        val center = state.layoutInfo.viewportEndOffset / 2
        val childCenter = it.offset + it.size / 2
        val target = (childCenter - center).toFloat()
        if (target in -delta..delta) {
            setOnWeekChange(number)
            weekLabel = stringResource(R.string.week)
            backgroundColor = color
            textColor = Color.White
        }
    }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .size(ITEM_DIAMETER.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    scope.launch {
                        state.animateScrollToItem(index = number - 1)
                    }
                },
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,

                ),
            border = BorderStroke(1.dp, color = color)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = textColor
                )
            }
        }
        Text(
            text = weekLabel,
            style = MaterialTheme.typography.bodyMedium,
            color = color
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 24.dp)
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
                        .size(40.dp)
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
            Text(
                text = text,
                maxLines = 5,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private const val ITEM_DIAMETER = 66