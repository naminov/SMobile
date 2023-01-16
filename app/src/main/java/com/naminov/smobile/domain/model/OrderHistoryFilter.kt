package com.naminov.smobile.domain.model

data class OrderHistoryFilter(
    val search: String,
    val customer: Customer?,
    val noPayment: Boolean,
    val noDocuments: Boolean
)