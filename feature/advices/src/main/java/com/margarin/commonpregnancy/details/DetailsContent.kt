package com.margarin.commonpregnancy.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.margarin.advices.R
import com.margarin.commonpregnancy.ui.theme.Green
import com.margarin.commonpregnancy.ui.theme.Pink
import com.margarin.commonpregnancy.ui.theme.Purple
import com.margarin.commonpregnancy.utils.ContentType

@Composable
fun DetailsContent(component: DetailsComponent) {

    val state by component.model.collectAsState()
    val currentState = state
    when (currentState.contentType) {
        ContentType.ChildDetails -> {
            Content(
                modifier = Modifier,
                component = component,
                imageResId = currentState.week.childImageResId,
                weekNumber = currentState.week.id + 1,
                title = stringResource(id = R.string.baby),
                text = currentState.week.childDetails,
                color = Pink
            )
        }

        ContentType.MotherDetails -> {
            Content(
                modifier = Modifier,
                component = component,
                imageResId = currentState.week.motherImageResId,
                weekNumber = currentState.week.id + 1,
                title = stringResource(id = R.string.mother),
                text = currentState.week.motherDetails,
                color = Purple
            )
        }

        ContentType.AdviceDetails -> {
            Content(
                modifier = Modifier,
                component = component,
                imageResId = R.drawable.todo_list_image,
                weekNumber = currentState.week.id + 1,
                title = stringResource(id = R.string.advices),
                text = currentState.week.adviceDetails,
                color = Green
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    modifier: Modifier,
    component: DetailsComponent,
    imageResId: Int?,
    weekNumber: Int,
    title: String,
    text: String,
    color: Color

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.TopEnd
    ) {
        imageResId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier
                    .height(250.dp),
                contentScale = ContentScale.FillHeight
            )
        }
    }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { component.onClickBack() }
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = color
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Transparent, color),
                                start = Offset(
                                    x = 0f,
                                    y = 350f
                                ),
                                end = Offset(
                                    x = 0f,
                                    y = 500f
                                )
                            )
                        )

                )
            }
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier.padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = weekNumber.toString() + " " + stringResource(id = R.string.week),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                        )
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .background(color)
                        .padding(start = 16.dp, end = 16.dp, bottom = 40.dp),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                )
            }
        }
    }
}