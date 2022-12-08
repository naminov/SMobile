package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.SettingsRepository
import com.naminov.smobile.domain.usecase.settings.SaveSettingsUseCase
import com.naminov.smobile.domain.usecase.settings.SaveSettingsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class SaveSettingsUseCaseModule {
    @Provides
    fun provideSaveSettingsUseCase(
        settingsRepository: SettingsRepository
    ): SaveSettingsUseCase {
        return SaveSettingsUseCaseImpl(settingsRepository)
    }
}