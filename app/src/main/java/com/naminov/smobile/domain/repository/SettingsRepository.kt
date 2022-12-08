package com.naminov.smobile.domain.repository

import com.naminov.smobile.domain.model.settings.Settings

interface SettingsRepository {
    suspend fun getSettings(): Settings
    suspend fun setSettings(settings: Settings)
}