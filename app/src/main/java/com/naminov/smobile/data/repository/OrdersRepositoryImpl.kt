package com.naminov.smobile.data.repository

import com.naminov.smobile.data.network.api.OrdersApi
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.repository.OrdersRepository

class OrdersRepositoryImpl(
   private val ordersApi: OrdersApi
): OrdersRepository {
    override suspend fun getOrderHistory(
        search: String?,
        customer: String?,
        payment: Boolean?,
        documents: Boolean?
    ): List<OrderHistory> {
        return ordersApi.getOrderHistory(search, customer, payment, documents)
            .map {
                OrderHistory(
                    it.id,
                    it.date,
                    it.customer,
                    it.sum,
                    it.completed,
                    it.documents,
                    it.payment
                )
            }
    }
}