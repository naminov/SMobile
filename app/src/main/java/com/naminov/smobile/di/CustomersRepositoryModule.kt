package com.naminov.smobile.di

import com.naminov.smobile.data.repository.CustomersRepositoryImpl
import com.naminov.smobile.domain.repository.CustomersRepository
import dagger.Module
import dagger.Provides

@Module
class CustomersRepositoryModule {
    @Provides
    fun provideCustomersRepository(): CustomersRepository {
        return CustomersRepositoryImpl()
    }
}