package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.*
import dagger.Module
import dagger.Provides

@Module
class RemoveOrderProductUseCaseModule {
    @Provides
    fun provideRemoveOrderProductUseCase(
        ordersRepository: OrdersRepository
    ): RemoveOrderProductUseCase {
        return RemoveOrderProductUseCaseImpl(ordersRepository)
    }
}