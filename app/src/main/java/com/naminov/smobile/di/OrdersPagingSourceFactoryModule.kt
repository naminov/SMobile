package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.OrdersApi
import com.naminov.smobile.data.network.paging.OrdersPagingSource
import dagger.Module
import dagger.Provides

@Module
class OrdersPagingSourceFactoryModule {
    @Provides
    fun provideOrdersPagingSourceFactory(
        ordersApi: OrdersApi
    ): OrdersPagingSource.Factory {
        return object : OrdersPagingSource.Factory {
            override fun create(
                search: String?,
                customer: String?,
                payment: Boolean?,
                documents: Boolean?
            ): OrdersPagingSource {
                return OrdersPagingSource(
                    ordersApi,
                    search,
                    customer,
                    payment,
                    documents
                )
            }
        }
    }
}