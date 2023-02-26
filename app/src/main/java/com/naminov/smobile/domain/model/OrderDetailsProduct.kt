package com.naminov.smobile.domain.model

data class OrderDetailsProduct(
    val id: String,
    val product: Product,
    val price: String,
    val number: Int,
    val sum: String,
    val editable: Boolean
)
