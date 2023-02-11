package com.naminov.smobile.domain.model

data class OrderDetails(
    val id: String,
    val number: String,
    val date: String,
    val customer: Customer,
    val sum: String,
    val products: List<OrderDetailsProduct>,
    val comment: String,
    val payment: Boolean,
    val documents: Boolean,
    val completed: Boolean,
    val editable: Boolean,
    val new: Boolean
)