package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.Authorization
import com.naminov.smobile.domain.repository.SettingsRepository

class GetAuthorizationUseCaseImpl(
    private val settingsRepository: SettingsRepository
): GetAuthorizationUseCase {
    override fun invoke(): Authorization {
        val settingsConnection = settingsRepository.getSettingsConnection()
        return settingsConnection.authorization
    }
}