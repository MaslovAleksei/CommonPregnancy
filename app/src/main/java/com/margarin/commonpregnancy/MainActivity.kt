package com.margarin.commonpregnancy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import com.margarin.commonpregnancy.presentation.root.DefaultRootComponent
import com.margarin.commonpregnancy.presentation.root.RootContent
import javax.inject.Inject

@ExperimentalDecomposeApi
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PregnancyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            RootContent(
                component = rootComponentFactory.create(defaultComponentContext()),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}