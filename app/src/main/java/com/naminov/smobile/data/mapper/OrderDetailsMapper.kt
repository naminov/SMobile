package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.OrderDetailsDto
import com.naminov.smobile.domain.model.OrderDetails

fun OrderDetailsDto.toDomain(): OrderDetails {
    return OrderDetails(
        id = id,
        number = number,
        date = date,
        customer = customer.toDomain(),
        sum = sum,
        products = products.toDomain(),
        comment = comment,
        payment = payment,
        documents = documents,
        completed = completed,
        editable = editable,
        new = new
    )
}