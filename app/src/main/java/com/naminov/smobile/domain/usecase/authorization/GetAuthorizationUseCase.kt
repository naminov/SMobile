package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.settings.Authorization

interface GetAuthorizationUseCase {
    suspend operator fun invoke(): Authorization
}