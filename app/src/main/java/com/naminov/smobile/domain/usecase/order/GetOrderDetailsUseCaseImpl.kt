package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.repository.OrdersRepository

class GetOrderDetailsUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : GetOrderDetailsUseCase {
    override suspend fun invoke(id: String): OrderDetails {
        return ordersRepository.getOrderDetails(id)
    }
}