package com.naminov.smobile.domain.usecase.login

import com.naminov.smobile.domain.model.settings.Authorization
import com.naminov.smobile.domain.repository.LoginRepository
import com.naminov.smobile.domain.usecase.authorization.SaveAuthorizationUseCase

class LoginUseCaseImpl(
    private val saveAuthorizationUseCase: SaveAuthorizationUseCase,
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override suspend fun invoke(authorization: Authorization) {
        saveAuthorizationUseCase(authorization)
        loginRepository.login()
    }
}