package com.naminov.smobile.data.repository

import com.naminov.smobile.data.mapper.toDomain
import com.naminov.smobile.data.mapper.toDto
import com.naminov.smobile.data.network.api.OrdersApi
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderEdit
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.repository.OrdersRepository

class OrdersRepositoryImpl(
    private val ordersApi: OrdersApi
) : OrdersRepository {
    override suspend fun getOrderHistory(
        search: String?,
        customer: String?,
        payment: Boolean?,
        documents: Boolean?
    ): List<OrderHistory> {
        return ordersApi
            .getOrderHistory(search?.ifEmpty { null }, customer, payment, documents)
            .toDomain()
    }

    override suspend fun getOrderDetails(id: String): OrderDetails {
        return ordersApi
            .getOrderDetails(id)
            .toDomain()
    }

    override suspend fun editOrder(id: String, orderEdit: OrderEdit): OrderDetails {
        return ordersApi
            .editOrder(id, orderEdit.toDto())
            .toDomain()
    }

    override suspend fun saveOrder(id: String, orderEdit: OrderEdit): OrderDetails {
        return ordersApi
            .saveOrder(id, orderEdit.toDto())
            .toDomain()
    }

    override suspend fun removeOrder(id: String) {
        ordersApi
            .removeOrder(id)
    }

    override suspend fun getOrderDetailsNew(customer: String?): OrderDetails {
        return ordersApi
            .getOrderDetailsNew(customer?.ifEmpty { null })
            .toDomain()
    }

    override suspend fun editOrderNew(orderEdit: OrderEdit): OrderDetails {
        return ordersApi
            .editOrderNew(orderEdit.toDto())
            .toDomain()
    }

    override suspend fun saveOrderNew(orderEdit: OrderEdit): OrderDetails {
        return ordersApi
            .saveOrderNew(orderEdit.toDto())
            .toDomain()
    }
}