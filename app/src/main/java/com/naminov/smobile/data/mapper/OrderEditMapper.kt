package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.OrderEditDto
import com.naminov.smobile.domain.model.OrderEdit

fun OrderEdit.toDto(): OrderEditDto {
    return OrderEditDto(
        customer = customer,
        products = products.toDto(),
        comment = comment
    )
}