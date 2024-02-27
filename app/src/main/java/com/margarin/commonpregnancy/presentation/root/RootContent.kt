package com.margarin.commonpregnancy.presentation.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.margarin.commonpregnancy.presentation.main.MainContent
import com.margarin.commonpregnancy.presentation.root.RootComponent.Child.MainChild
import com.margarin.commonpregnancy.presentation.root.RootComponent.Child.SettingsChild
import com.margarin.commonpregnancy.presentation.root.RootComponent.Child.ToDoChild
import com.margarin.commonpregnancy.presentation.settings.SettingsContent
import com.margarin.commonpregnancy.presentation.todo.ToDoContent
import com.margarin.commonpregnancy.presentation.ui.theme.CommonPregnancyTheme

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    CommonPregnancyTheme {
        Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
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
    }
}

@Composable
private fun Children(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is MainChild -> MainContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )

            is SettingsChild -> SettingsContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )

            is ToDoChild -> ToDoContent(
                component = child.component,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun BottomBar(component: RootComponent, modifier: Modifier = Modifier) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = activeComponent is MainChild,
            onClick = component::onMainTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                    contentDescription = "Counters",
                )
            }
        )

        NavigationBarItem(
            selected = activeComponent is ToDoChild,
            onClick = component::onToDoTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Cards",
                )
            }
        )

        NavigationBarItem(
            selected = activeComponent is SettingsChild,
            onClick = component::onSettingsTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Multi-Pane",
                )
            }
        )
    }
}