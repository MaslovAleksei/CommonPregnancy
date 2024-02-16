package com.margarin.commonpregnancy

import android.app.Application
import com.margarin.commonpregnancy.di.AppComponent
import com.margarin.commonpregnancy.di.DaggerAppComponent

class PregnancyApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}