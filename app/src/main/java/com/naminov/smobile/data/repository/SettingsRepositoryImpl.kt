package com.naminov.smobile.data.repository

import com.naminov.smobile.data.dao.SettingsDao
import com.naminov.smobile.data.dao.dto.AuthorizationDto
import com.naminov.smobile.data.dao.dto.SettingsConnectionDto
import com.naminov.smobile.domain.model.Authorization
import com.naminov.smobile.domain.model.SettingsConnection
import com.naminov.smobile.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDao: SettingsDao
): SettingsRepository {
    override fun getSettingsConnection(): SettingsConnection {
        val settingsConnectionDto = settingsDao.getSettingsConnection()
        val authorization = Authorization(
            settingsConnectionDto.authorization.userName,
            settingsConnectionDto.authorization.password
        )
        return SettingsConnection(
            settingsConnectionDto.url,
            authorization
        )
    }

    override fun setSettingsConnection(settingsConnection: SettingsConnection) {
        val authorization = AuthorizationDto(
            settingsConnection.authorization.userName,
            settingsConnection.authorization.password
        )
        val settingsConnectionDto = SettingsConnectionDto(
            settingsConnection.url,
            authorization
        )
        settingsDao.setSettingsConnection(settingsConnectionDto)
    }
}