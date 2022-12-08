package com.naminov.smobile.domain.usecase.settings

import com.naminov.smobile.domain.model.settings.Settings

interface GetSettingsUseCase {
    suspend operator fun invoke(): Settings
}