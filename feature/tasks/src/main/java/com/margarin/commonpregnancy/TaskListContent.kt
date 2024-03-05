package com.margarin.commonpregnancy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.ArrowDropDownCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.margarin.commonpregnancy.model.TaskCategory
import com.margarin.commonpregnancy.ui.theme.Green
import com.margarin.tasks.R

@Composable
fun TaskListContent(
    modifier: Modifier,
    component: TaskListComponent
) {
    val state by component.model.collectAsState()
    val titles = stringArrayResource(id = R.array.task_category_titles)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Header() }

        state.taskCategoryItems.forEachIndexed { index, taskCategoryItem ->
            taskListItem(
                state = state,
                component = component,
                taskCategoryItem = taskCategoryItem,
                title = titles[index]
            )
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.todo_list),
            style = MaterialTheme.typography.headlineLarge
        )
        Image(
            modifier = Modifier
                .height(150.dp),
            painter = painterResource(
                id = R.drawable.todo_list_image
            ),
            contentDescription = null
        )
    }
}

private fun LazyListScope.taskListItem(
    state: TaskListStore.State,
    component: TaskListComponent,
    taskCategoryItem: TaskListStore.State.TaskCategoryItem,
    title: String
) {
    val taskCategory = taskCategoryItem.taskCategory
    val isVisible = taskCategoryItem.isVisible

    item {
        Column {
            Row(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        component.onClickChangeVisibleState(
                            taskCategory = taskCategory,
                            isVisible = isVisible
                        )
                    }
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
                val rotateDegrees = if (isVisible) 180f else 0f
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .rotate(rotateDegrees),
                    imageVector = Icons.Outlined.ArrowDropDownCircle,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
            HorizontalDivider(color = Color.LightGray)
        }
    }

    if (isVisible) {
        items(
            items = filterTaskListByCategory(
                taskCategory = taskCategory,
                oldList = state.taskItems
            )
        ) { taskItem ->
            TaskItem(
                taskItem = taskItem,
                onCheckBoxClick = {
                    component.onClickCheckBox(taskItem.task)
                }
            )
        }
        item {
            HorizontalDivider(color = Color.LightGray)
        }
    }
}

@Composable
private fun TaskItem(
    taskItem: TaskListStore.State.TaskItem,
    onCheckBoxClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val isCompleted = taskItem.isCompleted

        IconButton(
            onClick = { onCheckBoxClick() },
        ) {
            Icon(
                imageVector = if (isCompleted) {
                    Icons.Filled.CheckCircle
                } else {
                    Icons.Outlined.Circle
                },
                tint = if (isCompleted) Green else Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = if (isCompleted) Color.White else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(width = 2.dp, shape = CircleShape, color = Green),
                contentDescription = null
            )
        }
        val textDecoration =
            if (isCompleted) TextDecoration.LineThrough else null
        val color = if (isCompleted) Color.Gray else Color.Black
        Text(
            text = taskItem.task.value,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            textDecoration = textDecoration
        )
    }
}

private fun filterTaskListByCategory(
    taskCategory: TaskCategory,
    oldList: List<TaskListStore.State.TaskItem>
): List<TaskListStore.State.TaskItem> {
    return when (taskCategory) {
        TaskCategory.FirstTrimester -> {
            oldList.filter {
                it.task.category is TaskCategory.FirstTrimester
            }
        }

        TaskCategory.SecondTrimester -> {
            oldList.filter {
                it.task.category is TaskCategory.SecondTrimester
            }
        }

        TaskCategory.ThirdTrimester -> {
            oldList.filter {
                it.task.category is TaskCategory.ThirdTrimester
            }
        }

        TaskCategory.ThingsForHospital -> {
            oldList.filter {
                it.task.category is TaskCategory.ThingsForHospital
            }
        }

        TaskCategory.ThingsAfterBirth -> {
            oldList.filter {
                it.task.category is TaskCategory.ThingsAfterBirth
            }
        }

        TaskCategory.ThingsForDischarge -> {
            oldList.filter {
                it.task.category is TaskCategory.ThingsForDischarge
            }
        }
    }
}