package com.naminov.smobile.data.repository

import com.naminov.smobile.data.network.api.LoginApi
import com.naminov.smobile.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val loginApi: LoginApi
): LoginRepository {
    override suspend fun login() {
        loginApi.login()
    }
}