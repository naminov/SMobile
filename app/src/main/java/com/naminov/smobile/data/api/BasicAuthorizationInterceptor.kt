package com.naminov.smobile.data.api

import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import okhttp3.Credentials
import okhttp3.Interceptor
import java.nio.charset.StandardCharsets

class BasicAuthorizationInterceptor(
    private val getAuthorizationUseCase: GetAuthorizationUseCase
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val authorization = getAuthorizationUseCase()

        val credentials: String = Credentials.basic(
            authorization.userName,
            authorization.password,
            StandardCharsets.UTF_8
        )

        val request = chain.request().newBuilder()
            .header("Authorization", credentials)
            .build()

        return chain.proceed(request)
    }
}