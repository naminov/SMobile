package com.naminov.smobile.data.dao

import com.naminov.smobile.data.dao.dto.AuthorizationDto
import com.naminov.smobile.data.dao.dto.SettingsConnectionDto

class SettingsDaoImpl: SettingsDao {
    private var connection = SettingsConnectionDto(
        url = "http://192.168.1.70/unf/hs/smobile/",
        authorization = AuthorizationDto("", "")
    )

    override fun getSettingsConnection(): SettingsConnectionDto {
        return connection
    }

    override fun setSettingsConnection(settingsConnectionDto: SettingsConnectionDto) {
        connection = settingsConnectionDto
    }
}