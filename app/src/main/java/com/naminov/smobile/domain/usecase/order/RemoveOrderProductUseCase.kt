package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderDetailsProduct

interface RemoveOrderProductUseCase {
    suspend operator fun invoke(
        orderDetails: OrderDetails,
        orderDetailsProduct: OrderDetailsProduct
    ): OrderDetails
}