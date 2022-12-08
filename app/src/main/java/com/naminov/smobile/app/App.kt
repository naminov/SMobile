package com.naminov.smobile.app

import android.app.Application
import com.naminov.smobile.di.AppComponent
import com.naminov.smobile.di.AppModule
import com.naminov.smobile.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}