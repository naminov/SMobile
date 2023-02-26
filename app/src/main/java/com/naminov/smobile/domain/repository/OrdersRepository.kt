package com.naminov.smobile.domain.repository

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderEdit
import com.naminov.smobile.domain.model.OrderHistory

interface OrdersRepository {
    fun getOrderHistory(
        search: String?,
        customer: String?,
        payment: Boolean?,
        documents: Boolean?
    ): PagingSource<Int, OrderHistory>
    suspend fun getOrderDetails(id: String): OrderDetails
    suspend fun editOrder(id: String, orderEdit: OrderEdit): OrderDetails
    suspend fun saveOrder(id: String, orderEdit: OrderEdit): OrderDetails
    suspend fun removeOrder(id: String)
    suspend fun getOrderDetailsNew(id: String?, customer: String?): OrderDetails
    suspend fun editOrderNew(orderEdit: OrderEdit): OrderDetails
    suspend fun saveOrderNew(orderEdit: OrderEdit): OrderDetails
}