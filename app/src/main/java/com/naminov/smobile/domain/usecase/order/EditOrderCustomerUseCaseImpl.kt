package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.repository.OrdersRepository

class EditOrderCustomerUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : EditOrderCustomerUseCase {
    override suspend fun invoke(
        orderDetails: OrderDetails,
        customer: Customer
    ): OrderDetails {
        if (orderDetails.customer == customer) {
            return orderDetails
        }

        val orderEdit = orderDetails.toEdit()
            .copy(
                customer = customer.id
            )

        return if (orderDetails.new) {
            ordersRepository.editOrderNew(orderEdit)
        } else {
            ordersRepository.editOrder(orderDetails.id, orderEdit)
        }
    }
}