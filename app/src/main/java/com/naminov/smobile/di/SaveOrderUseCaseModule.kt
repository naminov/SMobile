package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.SaveOrderUseCase
import com.naminov.smobile.domain.usecase.order.SaveOrderUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class SaveOrderUseCaseModule {
    @Provides
    fun provideSaveOrderUseCase(
        ordersRepository: OrdersRepository
    ): SaveOrderUseCase {
        return SaveOrderUseCaseImpl(ordersRepository)
    }
}