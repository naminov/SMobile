package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.model.OrderHistoryFilter

interface GetOrderHistoryUseCase {
    suspend operator fun invoke(filter: OrderHistoryFilter): List<OrderHistory>
}