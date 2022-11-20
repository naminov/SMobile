package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.LoginRepository
import com.naminov.smobile.domain.usecase.authorization.SaveAuthorizationUseCase
import com.naminov.smobile.domain.usecase.login.LoginUseCase
import com.naminov.smobile.domain.usecase.login.LoginUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class LoginUseCaseModule {
    @Provides
    fun provideLoginUseCase(
        saveAuthorizationUseCase: SaveAuthorizationUseCase,
        loginRepository: LoginRepository
    ): LoginUseCase {
        return LoginUseCaseImpl(
            saveAuthorizationUseCase,
            loginRepository
        )
    }
}