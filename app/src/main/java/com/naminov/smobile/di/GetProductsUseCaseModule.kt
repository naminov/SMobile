package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.ProductsRepository
import com.naminov.smobile.domain.usecase.product.GetProductsUseCase
import com.naminov.smobile.domain.usecase.product.GetProductsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetProductsUseCaseModule {
    @Provides
    fun provideGetProductsUseCase(
        productsRepository: ProductsRepository
    ): GetProductsUseCase {
        return GetProductsUseCaseImpl(productsRepository)
    }
}