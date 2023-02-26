package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.CustomersApi
import com.naminov.smobile.data.network.paging.CustomersPagingSource
import dagger.Module
import dagger.Provides

@Module
class CustomersPagingSourceFactoryModule {
    @Provides
    fun provideCustomersPagingSourceFactory(
        customersApi: CustomersApi
    ): CustomersPagingSource.Factory {
        return object : CustomersPagingSource.Factory {
            override fun create(
                search: String?
            ): CustomersPagingSource {
                return CustomersPagingSource(
                    customersApi,
                    search
                )
            }
        }
    }
}