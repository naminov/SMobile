package com.naminov.smobile.domain.usecase.settings

import com.naminov.smobile.domain.model.settings.Settings
import com.naminov.smobile.domain.repository.SettingsRepository

class SaveSettingsUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : SaveSettingsUseCase {
    override suspend fun invoke(settings: Settings) {
        return settingsRepository.setSettings(settings)
    }
}