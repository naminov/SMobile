package com.naminov.smobile.data.network.paging

import com.naminov.smobile.data.mapper.toDomain
import com.naminov.smobile.data.network.api.OrdersApi
import com.naminov.smobile.domain.model.OrderHistory

class OrdersPagingSource(
    private val ordersApi: OrdersApi,
    private val search: String?,
    private val customer: String?,
    private val payment: Boolean?,
    private val documents: Boolean?
) : BasePagingSource<OrderHistory>() {
    override suspend fun loadData(page: Int, pageSize: Int): List<OrderHistory> {
        return ordersApi.getOrderHistory(
            search = search?.ifEmpty { null },
            customer = customer,
            payment = payment,
            documents = documents,
            page = page,
            pageSize = pageSize
        ).toDomain()
    }

    interface Factory {
        fun create(
            search: String?,
            customer: String?,
            payment: Boolean?,
            documents: Boolean?
        ): OrdersPagingSource
    }
}