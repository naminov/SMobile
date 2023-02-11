package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails

interface GetOrderDetailsNewUseCase {
    suspend operator fun invoke(customer: String?): OrderDetails
}