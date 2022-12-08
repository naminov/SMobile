package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.settings.Authorization
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import com.naminov.smobile.domain.usecase.settings.SaveSettingsUseCase

class SaveAuthorizationUseCaseImpl(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
): SaveAuthorizationUseCase {
    override suspend fun invoke(authorization: Authorization) {
        val settings = getSettingsUseCase()
        val connection = settings.connection

        val settingsNew = settings.copy(
            connection = connection.copy(
                authorization = authorization
            )
        )
        saveSettingsUseCase(settingsNew)
    }
}