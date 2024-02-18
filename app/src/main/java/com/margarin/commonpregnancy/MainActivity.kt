package com.margarin.commonpregnancy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.margarin.commonpregnancy.presentation.ui.theme.CommonPregnancyTheme

//
//@Inject
//lateinit var rootComponentFactory: DefaultRootComponent.Factory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PregnancyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            CommonPregnancyTheme {

            }
        }
    }
}