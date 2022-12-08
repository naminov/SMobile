package com.naminov.smobile.di

import com.naminov.smobile.domain.usecase.connection.GetConnectionUseCase
import com.naminov.smobile.domain.usecase.connection.GetConnectionUseCaseImpl
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import dagger.Module
import dagger.Provides

@Module
class GetConnectionUseCaseModule {
    @Provides
    fun provideGetConnectionUseCase(
        getSettingsUseCase: GetSettingsUseCase
    ): GetConnectionUseCase {
        return GetConnectionUseCaseImpl(getSettingsUseCase)
    }
}