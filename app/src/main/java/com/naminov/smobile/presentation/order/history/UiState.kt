package com.naminov.smobile.presentation.order.history

import androidx.paging.PagingData
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.model.OrderHistoryFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class UiState(
    val initialized: Boolean = false,
    val filter: OrderHistoryFilter = OrderHistoryFilter(
        search = "",
        customer = null,
        noPayment = false,
        noDocuments = false
    ),
    val customerImg: String = "",
    val orders: Flow<PagingData<OrderHistory>> = flow {
        PagingData.empty<OrderHistory>()
    },
    val loading: Boolean = false
)