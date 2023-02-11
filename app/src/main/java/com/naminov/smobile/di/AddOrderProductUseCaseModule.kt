package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.AddOrderProductUseCase
import com.naminov.smobile.domain.usecase.order.AddOrderProductUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class AddOrderProductUseCaseModule {
    @Provides
    fun provideAddOrderProductUseCase(
        ordersRepository: OrdersRepository
    ): AddOrderProductUseCase {
        return AddOrderProductUseCaseImpl(ordersRepository)
    }
}