package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderDetailsProduct

interface EditOrderProductNumberUseCase {
    suspend operator fun invoke(
        orderDetails: OrderDetails,
        orderDetailsProduct: OrderDetailsProduct,
        number: Int
    ): OrderDetails
}