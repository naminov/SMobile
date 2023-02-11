package com.naminov.smobile.domain.usecase.order

import com.naminov.smobile.domain.mapper.toEdit
import com.naminov.smobile.domain.model.OrderDetails

class HasOrderChangesUseCaseImpl : HasOrderChangesUseCase {
    override suspend fun invoke(
        orderDetailsBefore: OrderDetails,
        orderDetailsAfter: OrderDetails
    ): Boolean {
        val orderEditBefore = orderDetailsBefore.toEdit()
        val orderEditAfter = orderDetailsAfter.toEdit()

        return if (orderDetailsAfter.new) {
            orderEditAfter.customer.isNotEmpty()
                    || orderEditAfter.products.isNotEmpty()
                    || orderEditAfter.comment.isNotEmpty()
        } else {
            orderEditBefore != orderEditAfter
        }
    }
}