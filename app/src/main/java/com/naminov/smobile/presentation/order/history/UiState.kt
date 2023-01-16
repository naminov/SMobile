package com.naminov.smobile.presentation.order.history

import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.model.OrderHistoryFilter

data class UiState(
    val filter: OrderHistoryFilter = OrderHistoryFilter(
        search = "",
        customer = null,
        noPayment = false,
        noDocuments = false
    ),
    val customerImg: String = "",
    val orders: List<OrderHistory> = listOf(),
    val loading: Boolean = false
)