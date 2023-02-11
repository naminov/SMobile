package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.GetOrderDetailsUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderDetailsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class GetOrderDetailsUseCaseModule {
    @Provides
    fun provideGetOrderDetailsUseCase(
        ordersRepository: OrdersRepository
    ): GetOrderDetailsUseCase {
        return GetOrderDetailsUseCaseImpl(ordersRepository)
    }
}