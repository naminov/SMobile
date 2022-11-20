package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.Authorization
import com.naminov.smobile.domain.repository.SettingsRepository

class SaveAuthorizationUseCaseImpl(
    private val settingsRepository: SettingsRepository
): SaveAuthorizationUseCase {
    override suspend fun invoke(authorization: Authorization) {
        val settingsConnection = settingsRepository.getSettingsConnection()
        val settingsConnectionNew = settingsConnection.copy(
            authorization = authorization
        )
        settingsRepository.setSettingsConnection(settingsConnectionNew)
    }
}