package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails

interface SaveOrderUseCase {
    suspend operator fun invoke(orderDetails: OrderDetails): OrderDetails
}