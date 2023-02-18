package com.naminov.smobile.di

import com.naminov.smobile.presentation.listener.DefaultSingleClickController
import com.naminov.smobile.presentation.listener.SingleClickController
import dagger.Module
import dagger.Provides

@Module
class SingleClickControllerModule {
    @Provides
    fun provideSingleClickController(): SingleClickController {
        return DefaultSingleClickController()
    }
}