package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.OrderHistoryDto
import com.naminov.smobile.domain.model.OrderHistory

fun OrderHistoryDto.toDomain(): OrderHistory {
    return OrderHistory(
        id = id,
        number = number,
        date = date,
        customer = customer,
        sum = sum,
        payment = payment,
        documents = documents,
        completed = completed
    )
}

fun List<OrderHistoryDto>.toDomain(): List<OrderHistory> {
    return map { it.toDomain() }
}