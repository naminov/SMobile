package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.OrderDetailsProductDto
import com.naminov.smobile.domain.model.OrderDetailsProduct

fun OrderDetailsProductDto.toDomain(): OrderDetailsProduct {
    return OrderDetailsProduct(
        id = id,
        product = product.toDomain(),
        price = price,
        number = number,
        sum = sum,
        editable = editable
    )
}

fun List<OrderDetailsProductDto>.toDomain(): List<OrderDetailsProduct> {
    return map { it.toDomain() }
}