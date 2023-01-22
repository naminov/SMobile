package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.CustomersApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CustomersApiModule {
    @Provides
    fun provideCustomersApi(retrofit: Retrofit): CustomersApi {
        return retrofit.create(CustomersApi::class.java)
    }
}