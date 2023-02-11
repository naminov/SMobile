package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.repository.OrdersRepository

class EditOrderCommentUseCaseImpl(
    private val ordersRepository: OrdersRepository
) : EditOrderCommentUseCase {
    override suspend fun invoke(
        orderDetails: OrderDetails,
        comment: String
    ): OrderDetails {
        if (orderDetails.comment == comment) {
            return orderDetails
        }

        val orderEdit = orderDetails.toEdit()
            .copy(
                comment = comment
            )

        return if (orderDetails.new) {
            ordersRepository.editOrderNew(orderEdit)
        } else {
            ordersRepository.editOrder(orderDetails.id, orderEdit)
        }
    }
}