package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.SettingsRepository
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetAuthorizationUseCaseModule {
    @Provides
    fun provideGetAuthorizationUseCase(
        settingsRepository: SettingsRepository
    ): GetAuthorizationUseCase {
        return GetAuthorizationUseCaseImpl(settingsRepository)
    }
}