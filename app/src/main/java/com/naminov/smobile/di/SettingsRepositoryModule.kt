package com.naminov.smobile.di

import com.naminov.smobile.data.db.dao.SettingsDao
import com.naminov.smobile.data.repository.SettingsRepositoryImpl
import com.naminov.smobile.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides

@Module
class SettingsRepositoryModule {
    @Provides
    fun provideSettingsRepository(
        settingsDao: SettingsDao
    ): SettingsRepository {
        return SettingsRepositoryImpl(settingsDao)
    }
}