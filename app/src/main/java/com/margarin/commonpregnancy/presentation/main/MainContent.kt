package com.margarin.commonpregnancy.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.margarin.commonpregnancy.R
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.HomeChild
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.SettingsChild
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.TaskChild
import com.margarin.commonpregnancy.HomeContent
import com.margarin.commonpregnancy.SettingsContent
import com.margarin.commonpregnancy.TaskListContent
import com.margarin.commonpregnancy.ui.theme.Green
import com.margarin.commonpregnancy.ui.theme.Pink
import com.margarin.commonpregnancy.ui.theme.Purple

@Composable
fun MainContent(component: MainComponent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
            ),
    ) {
        Children(component = component, modifier = Modifier.weight(1F))
        BottomBar(component = component, modifier = Modifier.fillMaxWidth())
    }

}

@Composable
private fun Children(component: MainComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is HomeChild -> HomeContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )

            is SettingsChild -> SettingsContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )

            is TaskChild -> TaskListContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun BottomBar(component: MainComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    NavigationBar(
        modifier = modifier.height(65.dp),
        contentColor = Color.White.copy(alpha = 0f),
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = activeComponent is HomeChild,
            onClick = component::onHomeTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                    contentDescription = "Counters",
                )
            },
            label = { Text(text = stringResource(R.string.advice)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray,
                indicatorColor = Pink
            )
        )

        NavigationBarItem(

            selected = activeComponent is TaskChild,
            onClick = component::onTaskTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Cards",
                )
            },
            label = { Text(text = stringResource(R.string.list)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray,
                indicatorColor = Green
            )
        )

        NavigationBarItem(
            selected = activeComponent is SettingsChild,
            onClick = component::onSettingsTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Multi-Pane",
                )
            },
            label = { Text(text = stringResource(R.string.settings)) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.Black,
                unselectedIconColor = Color.LightGray,
                unselectedTextColor = Color.LightGray,
                indicatorColor = Purple
            )
        )
    }
}