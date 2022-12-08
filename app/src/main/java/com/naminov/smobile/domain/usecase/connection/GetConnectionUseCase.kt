package com.naminov.smobile.domain.usecase.connection

import com.naminov.smobile.domain.model.settings.Connection

interface GetConnectionUseCase {
    suspend operator fun invoke(): Connection
}