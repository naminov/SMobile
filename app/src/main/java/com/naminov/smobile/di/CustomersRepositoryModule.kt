package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.CustomersApi
import com.naminov.smobile.data.repository.CustomersRepositoryImpl
import com.naminov.smobile.domain.repository.CustomersRepository
import dagger.Module
import dagger.Provides

@Module
class CustomersRepositoryModule {
    @Provides
    fun provideCustomersRepository(customersApi: CustomersApi): CustomersRepository {
        return CustomersRepositoryImpl(customersApi)
    }
}