package com.naminov.smobile.domain.repository

import com.naminov.smobile.domain.model.OrderHistory

interface OrdersRepository {
    suspend fun getOrderHistory(
        search: String?,
        customer: String?,
        payment: Boolean?,
        documents: Boolean?
    ): List<OrderHistory>
}