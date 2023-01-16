package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.OrdersApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class OrdersApiModule {
    @Provides
    fun provideOrdersApi(retrofit: Retrofit): OrdersApi {
        return retrofit.create(OrdersApi::class.java)
    }
}