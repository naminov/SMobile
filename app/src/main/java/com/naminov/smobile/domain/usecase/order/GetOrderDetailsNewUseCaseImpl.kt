package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.repository.OrdersRepository

class GetOrderDetailsNewUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : GetOrderDetailsNewUseCase {
    override suspend fun invoke(customer: String?): OrderDetails {
        return ordersRepository.getOrderDetailsNew(customer)
    }
}