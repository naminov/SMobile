package com.naminov.smobile.di

import com.naminov.smobile.data.network.api.OrdersApi
import com.naminov.smobile.data.repository.OrdersRepositoryImpl
import com.naminov.smobile.domain.repository.OrdersRepository
import dagger.Module
import dagger.Provides

@Module
class OrdersRepositoryModule {
    @Provides
    fun provideOrdersRepository(ordersApi: OrdersApi): OrdersRepository {
        return OrdersRepositoryImpl(ordersApi)
    }
}