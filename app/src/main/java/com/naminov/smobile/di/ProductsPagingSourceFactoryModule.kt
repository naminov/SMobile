package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.ProductsApi
import com.naminov.smobile.data.network.paging.ProductsPagingSource
import dagger.Module
import dagger.Provides

@Module
class ProductsPagingSourceFactoryModule {
    @Provides
    fun provideProductsPagingSourceFactory(
        productsApi: ProductsApi
    ): ProductsPagingSource.Factory {
        return object : ProductsPagingSource.Factory {
            override fun create(
                search: String?
            ): ProductsPagingSource {
                return ProductsPagingSource(
                    productsApi,
                    search
                )
            }
        }
    }
}