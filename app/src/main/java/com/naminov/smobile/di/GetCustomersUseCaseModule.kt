package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.CustomersRepository
import com.naminov.smobile.domain.usecase.customer.GetCustomersUseCase
import com.naminov.smobile.domain.usecase.customer.GetCustomersUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetCustomersUseCaseModule {
    @Provides
    fun provideGetCustomersUseCase(
        customersRepository: CustomersRepository
    ): GetCustomersUseCase {
        return GetCustomersUseCaseImpl(customersRepository)
    }
}