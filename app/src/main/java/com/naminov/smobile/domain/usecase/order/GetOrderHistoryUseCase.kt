package com.naminov.smobile.domain.usecase.order

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.model.OrderHistoryFilter

interface GetOrderHistoryUseCase {
    operator fun invoke(filter: OrderHistoryFilter): PagingSource<Int, OrderHistory>
}