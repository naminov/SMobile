package com.naminov.smobile.data.api

import retrofit2.http.GET

interface LoginApi {
    @GET("login/")
    suspend fun login()
}