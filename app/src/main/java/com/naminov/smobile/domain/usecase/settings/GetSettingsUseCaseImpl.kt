package com.naminov.smobile.domain.usecase.settings

import com.naminov.smobile.domain.model.settings.Settings
import com.naminov.smobile.domain.repository.SettingsRepository

class GetSettingsUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : GetSettingsUseCase {
    override suspend fun invoke(): Settings {
        return settingsRepository.getSettings()
    }
}