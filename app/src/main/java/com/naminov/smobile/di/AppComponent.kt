package com.naminov.smobile.di

import com.naminov.smobile.presentation.login.LoginFragment
import com.naminov.smobile.presentation.settings.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        SettingsDaoModule::class,
        SettingsRepositoryModule::class,
        LoginApiModule::class,
        LoginRepositoryModule::class,
        LoginUseCaseModule::class,
        GetSettingsUseCaseModule::class,
        SaveSettingsUseCaseModule::class,
        GetConnectionUseCaseModule::class,
        GetAuthorizationUseCaseModule::class,
        SaveAuthorizationUseCaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(loginFragment: LoginFragment)
    fun inject(settingsFragment: SettingsFragment)
}