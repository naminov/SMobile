package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.EditOrderCustomerUseCase
import com.naminov.smobile.domain.usecase.order.EditOrderCustomerUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class EditOrderCustomerUseCaseModule {
    @Provides
    fun provideEditOrderCustomerUseCase(
        ordersRepository: OrdersRepository
    ): EditOrderCustomerUseCase {
        return EditOrderCustomerUseCaseImpl(ordersRepository)
    }
}