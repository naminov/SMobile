package com.naminov.smobile.domain.model

data class OrderEdit(
    val customer: String,
    val products: List<OrderEditProduct>,
    val comment: String
)