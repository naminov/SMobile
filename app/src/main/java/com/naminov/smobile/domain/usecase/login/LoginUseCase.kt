package com.naminov.smobile.domain.usecase.login

import com.naminov.smobile.domain.model.Authorization

interface LoginUseCase {
    suspend operator fun invoke(authorization: Authorization)
}