package com.naminov.smobile.domain.repository

import com.naminov.smobile.domain.model.SettingsConnection

interface SettingsRepository {
    fun getSettingsConnection(): SettingsConnection
    fun setSettingsConnection(settingsConnection: SettingsConnection)
}