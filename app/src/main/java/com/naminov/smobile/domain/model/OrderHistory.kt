package com.naminov.smobile.domain.model

data class OrderHistory(
    val id: String,
    val number: String,
    val date: String,
    val customer: String,
    val sum: String,
    val payment: Boolean,
    val documents: Boolean,
    val completed: Boolean
)