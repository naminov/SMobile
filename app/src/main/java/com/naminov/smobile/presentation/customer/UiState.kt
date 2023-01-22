package com.naminov.smobile.presentation.customer

import com.naminov.smobile.domain.model.Customer

data class UiState(
    val search: String = "",
    val customers: List<Customer> = listOf(),
    val loading: Boolean = false
)