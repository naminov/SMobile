package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.CustomersRepository
import com.naminov.smobile.domain.usecase.customer.GetCustomerImgUseCase
import com.naminov.smobile.domain.usecase.customer.GetCustomerImgUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetCustomerImgUseCaseModule {
    @Provides
    fun provideGetCustomerImgUseCase(
        customersRepository: CustomersRepository
    ): GetCustomerImgUseCase {
        return GetCustomerImgUseCaseImpl(customersRepository)
    }
}