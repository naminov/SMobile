package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.OrderEditProductDto
import com.naminov.smobile.domain.model.OrderEditProduct

fun OrderEditProduct.toDto(): OrderEditProductDto {
    return OrderEditProductDto(
        id = id,
        number = number,
    )
}

fun List<OrderEditProduct>.toDto(): List<OrderEditProductDto> {
    return map { it.toDto() }
}