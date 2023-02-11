package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.ProductsApi
import com.naminov.smobile.data.repository.ProductsRepositoryImpl
import com.naminov.smobile.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides

@Module
class ProductsRepositoryModule {
    @Provides
    fun provideProductsRepository(productsApi: ProductsApi): ProductsRepository {
        return ProductsRepositoryImpl(productsApi)
    }
}