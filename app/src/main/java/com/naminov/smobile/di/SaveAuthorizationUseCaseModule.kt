package com.naminov.smobile.di

import com.naminov.smobile.domain.usecase.authorization.SaveAuthorizationUseCase
import com.naminov.smobile.domain.usecase.authorization.SaveAuthorizationUseCaseImpl
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import com.naminov.smobile.domain.usecase.settings.SaveSettingsUseCase
import dagger.Module
import dagger.Provides

@Module
class SaveAuthorizationUseCaseModule {
    @Provides
    fun provideSaveAuthorizationUseCase(
        getSettingsUseCase: GetSettingsUseCase,
        saveSettingsUseCase: SaveSettingsUseCase
    ): SaveAuthorizationUseCase {
        return SaveAuthorizationUseCaseImpl(
            getSettingsUseCase,
            saveSettingsUseCase
        )
    }
}