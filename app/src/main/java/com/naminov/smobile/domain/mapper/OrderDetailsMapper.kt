package com.naminov.smobile.domain.mapper

import com.naminov.smobile.domain.model.OrderDetails
import com.naminov.smobile.domain.model.OrderEdit

fun OrderDetails.toEdit(): OrderEdit {
    return OrderEdit(
        customer = customer.id,
        products = products.toEdit(),
        comment = comment
    )
}