package com.naminov.smobile.di

import com.naminov.smobile.data.dao.SettingsDao
import com.naminov.smobile.data.dao.SettingsDaoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsDaoModule {
    @Provides
    @Singleton
    fun provideSettingsDao(): SettingsDao {
        return SettingsDaoImpl()
    }
}