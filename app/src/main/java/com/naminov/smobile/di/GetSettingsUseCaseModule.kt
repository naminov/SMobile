package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.SettingsRepository
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetSettingsUseCaseModule {
    @Provides
    fun provideGetSettingsUseCase(
        settingsRepository: SettingsRepository
    ): GetSettingsUseCase {
        return GetSettingsUseCaseImpl(settingsRepository)
    }
}