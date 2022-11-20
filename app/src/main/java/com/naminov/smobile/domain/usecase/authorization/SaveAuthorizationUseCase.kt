package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.Authorization

interface SaveAuthorizationUseCase {
    suspend operator fun invoke(authorization: Authorization)
}