package com.naminov.smobile.domain.usecase.authorization

import com.naminov.smobile.domain.model.Authorization

interface GetAuthorizationUseCase {
    operator fun invoke(): Authorization
}