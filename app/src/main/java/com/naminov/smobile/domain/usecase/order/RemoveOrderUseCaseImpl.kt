package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.repository.OrdersRepository

class RemoveOrderUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : RemoveOrderUseCase {
    override suspend fun invoke(id: String) {
        ordersRepository.removeOrder(id)
    }
}