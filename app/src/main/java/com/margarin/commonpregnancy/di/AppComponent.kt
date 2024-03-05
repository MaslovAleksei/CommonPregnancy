package com.margarin.commonpregnancy.di

import android.content.Context
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.margarin.commonpregnancy.AppScope
import com.margarin.commonpregnancy.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        PresentationModule::class
    ]
)
interface AppComponent {

    @OptIn(ExperimentalDecomposeApi::class)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}