package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails

interface EditOrderCommentUseCase {
    suspend operator fun invoke(
        orderDetails: OrderDetails,
        comment: String
    ): OrderDetails
}