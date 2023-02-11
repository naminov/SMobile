package com.naminov.smobile.data.mapper

import com.naminov.smobile.data.network.dto.CustomerDto
import com.naminov.smobile.domain.model.Customer

fun CustomerDto.toDomain(): Customer {
    return Customer(
        id = id,
        name = name
    )
}

fun List<CustomerDto>.toDomain(): List<Customer> {
    return map { it.toDomain() }
}