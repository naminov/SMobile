package com.naminov.smobile.data.dao

import com.naminov.smobile.data.dao.dto.SettingsConnectionDto

interface SettingsDao {
    fun getSettingsConnection(): SettingsConnectionDto
    fun setSettingsConnection(settingsConnectionDto: SettingsConnectionDto)
}