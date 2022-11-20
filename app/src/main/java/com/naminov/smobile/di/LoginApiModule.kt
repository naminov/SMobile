package com.naminov.smobile.di

import com.naminov.smobile.data.api.LoginApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class LoginApiModule {
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }
}