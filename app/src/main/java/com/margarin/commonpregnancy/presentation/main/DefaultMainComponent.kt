package com.margarin.commonpregnancy.presentation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.home.DefaultHomeComponent
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child
import com.margarin.commonpregnancy.presentation.main.MainComponent.Child.*
import com.margarin.commonpregnancy.presentation.main.settings.DefaultSettingsComponent
import com.margarin.commonpregnancy.presentation.main.tasklist.DefaultTaskListComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

@OptIn(ExperimentalDecomposeApi::class)
class DefaultMainComponent @AssistedInject constructor(
    private val mainComponentFactory: DefaultHomeComponent.Factory,
    private val settingsComponentFactory: DefaultSettingsComponent.Factory,
    private val taskListComponentFactory: DefaultTaskListComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext {
    private val deepLink: DeepLink = DeepLink.None
    private val webHistoryController: WebHistoryController? = null

    private val navigation = StackNavigation<Config>()

    @OptIn(ExperimentalDecomposeApi::class)
    private val stack =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialStack = {
                getInitialStack(
                    webHistoryPaths = webHistoryController?.historyPaths,
                    deepLink = deepLink
                )
            },
            childFactory = ::child,
        )

    override val childStack: Value<ChildStack<*, Child>> = stack

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Home -> {
                val component = mainComponentFactory.create(componentContext = componentContext)
                HomeChild(component)
            }

            is Config.Settings -> {
                val component = settingsComponentFactory.create(componentContext = componentContext)
                SettingsChild(component)
            }

            is Config.Task -> {
                val component = taskListComponentFactory.create(componentContext = componentContext)
                TaskChild(component)
            }
        }

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.Home)
    }

    override fun onTaskTabClicked() {
        navigation.bringToFront(Config.Task)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    private companion object {
        private const val WEB_PATH_HOME = "home"
        private const val WEB_PATH_TASK = "task"
        private const val WEB_PATH_SETTINGS = "settings"

        private fun getInitialStack(
            webHistoryPaths: List<String>?,
            deepLink: DeepLink
        ): List<Config> =
            webHistoryPaths
                ?.takeUnless(List<*>::isEmpty)
                ?.map(::getConfigForPath)
                ?: getInitialStack(deepLink)

        private fun getInitialStack(deepLink: DeepLink): List<Config> =
            when (deepLink) {
                is DeepLink.None -> listOf(Config.Home)
                is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
            }

        private fun getPathForConfig(config: Config): String =
            when (config) {
                Config.Home -> "/$WEB_PATH_HOME"
                Config.Settings -> "/$WEB_PATH_SETTINGS"
                Config.Task -> "/$WEB_PATH_TASK"
            }

        private fun getConfigForPath(path: String): Config =
            when (path.removePrefix("/")) {
                WEB_PATH_HOME -> Config.Home
                WEB_PATH_SETTINGS -> Config.Settings
                WEB_PATH_TASK -> Config.Task
                else -> Config.Home
            }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data object Task : Config

        @Serializable
        data object Settings : Config
    }

    sealed interface DeepLink {
        data object None : DeepLink
        class Web(val path: String) : DeepLink
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultMainComponent
    }
}