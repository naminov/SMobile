package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.GetOrderHistoryUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderHistoryUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetOrderHistoryUseCaseModule {
    @Provides
    fun provideGetOrderHistoryUseCase(
        ordersRepository: OrdersRepository
    ): GetOrderHistoryUseCase {
        return GetOrderHistoryUseCaseImpl(ordersRepository)
    }
}