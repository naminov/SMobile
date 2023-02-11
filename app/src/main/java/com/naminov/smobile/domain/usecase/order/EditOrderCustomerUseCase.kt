package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.model.OrderDetails

interface EditOrderCustomerUseCase {
    suspend operator fun invoke(
        orderDetails: OrderDetails,
        customer: Customer
    ): OrderDetails
}