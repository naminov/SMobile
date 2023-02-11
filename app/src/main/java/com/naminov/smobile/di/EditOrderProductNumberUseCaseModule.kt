package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.EditOrderProductNumberUseCase
import com.naminov.smobile.domain.usecase.order.EditOrderProductNumberUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class EditOrderProductNumberUseCaseModule {
    @Provides
    fun provideEditOrderProductNumberUseCase(
        ordersRepository: OrdersRepository
    ): EditOrderProductNumberUseCase {
        return EditOrderProductNumberUseCaseImpl(ordersRepository)
    }
}