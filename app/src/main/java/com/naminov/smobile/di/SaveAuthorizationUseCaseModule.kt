package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.SettingsRepository
import com.naminov.smobile.domain.usecase.authorization.SaveAuthorizationUseCase
import com.naminov.smobile.domain.usecase.authorization.SaveAuthorizationUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class SaveAuthorizationUseCaseModule {
    @Provides
    fun provideSaveAuthorizationUseCase(
        settingsRepository: SettingsRepository
    ): SaveAuthorizationUseCase {
        return SaveAuthorizationUseCaseImpl(settingsRepository)
    }
}