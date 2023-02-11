package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.GetOrderDetailsNewUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderDetailsNewUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetOrderDetailsNewUseCaseModule {
    @Provides
    fun provideGetOrderDetailsNewUseCase(
        ordersRepository: OrdersRepository
    ): GetOrderDetailsNewUseCase {
        return GetOrderDetailsNewUseCaseImpl(ordersRepository)
    }
}