package com.naminov.smobile.data.repository

import com.naminov.smobile.data.db.dao.SettingsDao
import com.naminov.smobile.data.db.entity.SettingsEntity
import com.naminov.smobile.domain.model.settings.Authorization
import com.naminov.smobile.domain.model.settings.Settings
import com.naminov.smobile.domain.model.settings.Connection
import com.naminov.smobile.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDao: SettingsDao
): SettingsRepository {
    override suspend fun getSettings(): Settings {
        val settingsEntities = settingsDao.getSettings()
        val connectionUrl = settingsEntities.find {
            it.key == "connection_url"
        }?.value ?: ""
        val username = settingsEntities.find {
            it.key == "user_name"
        }?.value ?: ""
        val password = settingsEntities.find {
            it.key == "password"
        }?.value ?: ""

        val authorization = Authorization(
            username,
            password
        )
        val connection = Connection(
            connectionUrl,
            authorization
        )
        return Settings(connection)
    }

    override suspend fun setSettings(settings: Settings) {
        val connectionUrl = SettingsEntity(
            "connection_url", settings.connection.url
        )
        val userName = SettingsEntity(
            "user_name", settings.connection.authorization.userName
        )
        val password = SettingsEntity(
            "password", settings.connection.authorization.password
        )

        val settingsEntities = listOf(
            connectionUrl,
            userName,
            password
        )
        settingsDao.setSettings(settingsEntities)
    }
}