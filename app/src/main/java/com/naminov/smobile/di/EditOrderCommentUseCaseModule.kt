package com.naminov.smobile.di

import com.naminov.smobile.domain.repository.OrdersRepository
import com.naminov.smobile.domain.usecase.order.EditOrderCommentUseCase
import com.naminov.smobile.domain.usecase.order.EditOrderCommentUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class EditOrderCommentUseCaseModule {
    @Provides
    fun provideEditOrderCommentUseCase(
        ordersRepository: OrdersRepository
    ): EditOrderCommentUseCase {
        return EditOrderCommentUseCaseImpl(ordersRepository)
    }
}