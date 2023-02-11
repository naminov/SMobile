package com.naminov.smobile.presentation.product

import com.naminov.smobile.domain.model.Product

data class UiState(
    val search: String = "",
    val products: List<Product> = listOf(),
    val loading: Boolean = false
)