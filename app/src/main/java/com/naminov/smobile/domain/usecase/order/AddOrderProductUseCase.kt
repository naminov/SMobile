package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.Product

interface AddOrderProductUseCase {
    suspend operator fun invoke(
        orderDetails: OrderDetails,
        product: Product,
    ): OrderDetails
}