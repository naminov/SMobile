package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderDetails

interface GetOrderDetailsUseCase {
    suspend operator fun invoke(id: String): OrderDetails
}