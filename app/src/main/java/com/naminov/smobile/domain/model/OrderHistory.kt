package com.naminov.smobile.domain.model

data class OrderHistory(
    val id: String,
    val date: String,
    val customer: String,
    val sum: String,
    val completed: Boolean,
    val documents: Boolean,
    val payment: Boolean
)