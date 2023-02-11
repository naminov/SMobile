package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.ProductsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProductsApiModule {
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }
}