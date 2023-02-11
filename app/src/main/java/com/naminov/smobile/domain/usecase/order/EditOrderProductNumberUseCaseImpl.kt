package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderDetailsProduct
import com.naminov.smobile.domain.model.OrderEditProduct
import com.naminov.smobile.domain.repository.OrdersRepository

class EditOrderProductNumberUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : EditOrderProductNumberUseCase {
    override suspend fun invoke(
        orderDetails: OrderDetails,
        orderDetailsProduct: OrderDetailsProduct,
        number: Int
    ): OrderDetails {
        if (orderDetailsProduct.number == number) {
            return orderDetails
        }

        val orderEditable = orderDetails.toEdit()

        val products = orderEditable.products
            .map {
                val productId = orderDetailsProduct.product.id

                if (it.id == productId) {
                    OrderEditProduct(productId, number)
                } else {
                    it
                }
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