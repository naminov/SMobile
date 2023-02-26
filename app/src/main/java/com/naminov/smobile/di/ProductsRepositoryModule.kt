package com.naminov.smobile.di

import com.naminov.smobile.data.network.paging.ProductsPagingSource
import com.naminov.smobile.data.repository.ProductsRepositoryImpl
import com.naminov.smobile.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides

@Module
class ProductsRepositoryModule {
    @Provides
    fun provideProductsRepository(
        productsPagingSourceFactory: ProductsPagingSource.Factory
    ): ProductsRepository {
        return ProductsRepositoryImpl(
            productsPagingSourceFactory
        )
    }
}