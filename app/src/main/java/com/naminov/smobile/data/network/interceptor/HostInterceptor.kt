package com.naminov.smobile.data.network.interceptor

import com.naminov.smobile.domain.usecase.connection.GetConnectionUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor

class HostInterceptor(
    private val baseUrl: String,
    private val getConnectionUseCase: GetConnectionUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val connection = runBlocking { getConnectionUseCase() }

        val connectionUrl = connection.url.toHttpUrlOrNull()
            ?: return chain.proceed(chain.request())

        val newUrl = chain.request().url.toString().replace(
            baseUrl,
            connectionUrl.toString()
        )

        val request = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}