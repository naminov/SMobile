package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderEditProduct
import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.domain.repository.OrdersRepository

class AddOrderProductUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : AddOrderProductUseCase {
    override suspend fun invoke(
        orderDetails: OrderDetails,
        product: Product
    ): OrderDetails {
        val orderContainsProduct = orderDetails.products.any {
            it.product.id == product.id
        }

        if (orderContainsProduct) {
            return orderDetails
        }

        val orderEditable = orderDetails.toEdit()

        val orderEditProduct = OrderEditProduct(
            product.id,
            1
        )

        val products = orderEditable.products
            .toMutableList()
            .apply { add(orderEditProduct) }

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