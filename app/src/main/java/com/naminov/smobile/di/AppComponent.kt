package com.naminov.smobile.di

import com.naminov.smobile.presentation.login.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        SettingsDaoModule::class,
        SettingsRepositoryModule::class,
        LoginApiModule::class,
        LoginRepositoryModule::class,
        LoginUseCaseModule::class,
        GetAuthorizationUseCaseModule::class,
        SaveAuthorizationUseCaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(loginFragment: LoginFragment)
}