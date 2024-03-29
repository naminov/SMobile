package com.naminov.smobile.domain.usecase.order

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.model.OrderHistoryFilter
import com.naminov.smobile.domain.repository.OrdersRepository

class GetOrderHistoryUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : GetOrderHistoryUseCase {
    override fun invoke(filter: OrderHistoryFilter): PagingSource<Int, OrderHistory> {
        val search = filter.search
        val customer = filter.customer?.id
        val payment = if (filter.noPayment) false else null
        val documents = if (filter.noDocuments) false else null

        return ordersRepository.getOrderHistory(search, customer, payment, documents)
    }
}