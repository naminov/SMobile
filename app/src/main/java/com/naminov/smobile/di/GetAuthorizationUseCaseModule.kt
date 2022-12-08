package com.naminov.smobile.di

import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCaseImpl
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import dagger.Module
import dagger.Provides

@Module
class GetAuthorizationUseCaseModule {
    @Provides
    fun provideGetAuthorizationUseCase(
        getSettingsUseCase: GetSettingsUseCase
    ): GetAuthorizationUseCase {
        return GetAuthorizationUseCaseImpl(getSettingsUseCase)
    }
}