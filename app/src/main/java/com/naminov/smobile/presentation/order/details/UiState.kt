package com.naminov.smobile.presentation.order.details

import com.naminov.smobile.domain.model.*

data class UiState(
    val initialized: Boolean = false,
    val orderDetailsOrig: OrderDetails = OrderDetails(
        id = "",
        number = "",
        date = "",
        customer = Customer(
            id = "",
            name = ""
        ),
        sum = "",
        products = listOf(),
        comment = "",
        payment = true,
        documents = true,
        completed = false,
        editable = false,
        new = false
    ),
    val orderDetails: OrderDetails = orderDetailsOrig,
    val loading: Boolean = false
)