package com.naminov.smobile.di

import com.naminov.smobile.data.db.Database
import com.naminov.smobile.data.db.dao.SettingsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsDaoModule {
    @Provides
    @Singleton
    fun provideSettingsDao(
        database: Database
    ): SettingsDao {
        return database.SettingsDao()
    }
}