package com.naminov.smobile.data.network.api

import retrofit2.http.GET

interface LoginApi {
    @GET("login/")
    suspend fun login()
}