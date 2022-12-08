package com.naminov.smobile.domain.usecase.settings

import com.naminov.smobile.domain.model.settings.Settings

interface SaveSettingsUseCase {
    suspend operator fun invoke(settings: Settings)
}