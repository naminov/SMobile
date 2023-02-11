package com.naminov.smobile.domain.mapper

import com.naminov.smobile.domain.model.OrderDetailsProduct
import com.naminov.smobile.domain.model.OrderEditProduct

fun OrderDetailsProduct.toEdit(): OrderEditProduct {
    return OrderEditProduct(
        id = product.id,
        number = number
    )
}

fun List<OrderDetailsProduct>.toEdit(): List<OrderEditProduct> {
    return map { it.toEdit() }
}