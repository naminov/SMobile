package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.ProductDto
import com.naminov.smobile.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        name = name
    )
}

fun List<ProductDto>.toDomain(): List<Product> {
    return map { it.toDomain() }
}