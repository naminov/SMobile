package com.naminov.smobile.domain.usecase.connection

import com.naminov.smobile.domain.model.settings.Connection
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase

class GetConnectionUseCaseImpl(
    private val getSettingsUseCase: GetSettingsUseCase
) : GetConnectionUseCase {
    override suspend fun invoke(): Connection {
        val settings = getSettingsUseCase()
        return settings.connection
    }
}