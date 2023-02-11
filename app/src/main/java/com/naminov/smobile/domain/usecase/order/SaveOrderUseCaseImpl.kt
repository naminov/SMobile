package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.repository.OrdersRepository

class SaveOrderUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : SaveOrderUseCase {
    override suspend fun invoke(orderDetails: OrderDetails): OrderDetails {
        val orderEdit = orderDetails.toEdit()

        return if (orderDetails.new) {
            ordersRepository.saveOrderNew(orderEdit)
        } else {
            ordersRepository.saveOrder(orderDetails.id, orderEdit)
        }
    }
}