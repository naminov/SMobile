package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails

interface HasOrderChangesUseCase {
    suspend operator fun invoke(
        orderDetailsBefore: OrderDetails,
        orderDetailsAfter: OrderDetails
    ): Boolean
}