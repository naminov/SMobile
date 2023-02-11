package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.RemoveOrderUseCase
import com.naminov.smobile.domain.usecase.order.RemoveOrderUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class RemoveOrderUseCaseModule {
    @Provides
    fun provideRemoveOrderUseCase(
        ordersRepository: OrdersRepository
    ): RemoveOrderUseCase {
        return RemoveOrderUseCaseImpl(ordersRepository)
    }
}