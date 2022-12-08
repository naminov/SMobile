package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.settings.Authorization
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase

class GetAuthorizationUseCaseImpl(
    private val getSettingsUseCase: GetSettingsUseCase
): GetAuthorizationUseCase {
    override suspend fun invoke(): Authorization {
        val settings = getSettingsUseCase()
        val connection = settings.connection
        return connection.authorization
    }
}