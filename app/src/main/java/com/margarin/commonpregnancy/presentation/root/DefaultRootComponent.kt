package com.margarin.commonpregnancy.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import com.margarin.commonpregnancy.presentation.main.DefaultMainComponent
import com.margarin.commonpregnancy.presentation.root.RootComponent.Child
import com.margarin.commonpregnancy.presentation.root.RootComponent.Child.*
import com.margarin.commonpregnancy.presentation.settings.DefaultSettingsComponent
import com.margarin.commonpregnancy.presentation.todo.DefaultToDoComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

@OptIn(ExperimentalDecomposeApi::class)
class DefaultRootComponent @AssistedInject constructor(
    private val mainComponentFactory: DefaultMainComponent.Factory,
    private val settingsComponentFactory: DefaultSettingsComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
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
            is Config.Main -> {
                val component = mainComponentFactory.create(componentContext = componentContext)
                MainChild(component)
            }

            is Config.Settings -> {
                val component = settingsComponentFactory.create(componentContext = componentContext)
                SettingsChild(component)
            }
            is Config.ToDo -> ToDoChild(DefaultToDoComponent(componentContext))
        }

    override fun onMainTabClicked() {
        navigation.bringToFront(Config.Main)
    }

    override fun onToDoTabClicked() {
        navigation.bringToFront(Config.ToDo)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    private companion object {
        private const val WEB_PATH_MAIN = "main"
        private const val WEB_PATH_TODO = "todo"
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
                is DeepLink.None -> listOf(Config.Main)
                is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
            }

        private fun getPathForConfig(config: Config): String =
            when (config) {
                Config.Main -> "/$WEB_PATH_MAIN"
                Config.Settings -> "/$WEB_PATH_SETTINGS"
                Config.ToDo -> "/$WEB_PATH_TODO"
            }

        private fun getConfigForPath(path: String): Config =
            when (path.removePrefix("/")) {
                WEB_PATH_MAIN -> Config.Main
                WEB_PATH_SETTINGS -> Config.Settings
                WEB_PATH_TODO -> Config.ToDo
                else -> Config.Main
            }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Main : Config

        @Serializable
        data object ToDo : Config

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
        ): DefaultRootComponent
    }
}