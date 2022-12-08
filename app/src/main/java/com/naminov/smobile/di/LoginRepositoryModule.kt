package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.LoginApi
import com.naminov.smobile.data.repository.LoginRepositoryImpl
import com.naminov.smobile.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides

@Module
class LoginRepositoryModule {
    @Provides
    fun provideLoginRepository(loginApi: LoginApi): LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }
}