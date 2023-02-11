package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderDetailsProduct
import com.naminov.smobile.domain.repository.OrdersRepository

class RemoveOrderProductUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : RemoveOrderProductUseCase {
    override suspend fun invoke(
        orderDetails: OrderDetails,
        orderDetailsProduct: OrderDetailsProduct
    ): OrderDetails {
        val orderEditable = orderDetails.toEdit()

        val products = orderEditable.products
            .filter { product ->
                product.id != orderDetailsProduct.product.id
            }

        val orderEdit = orderEditable
            .copy(
                products = products
            )

        return if (orderDetails.new) {
            ordersRepository.editOrderNew(orderEdit)
        } else {
            ordersRepository.editOrder(orderDetails.id, orderEdit)
        }
    }
}