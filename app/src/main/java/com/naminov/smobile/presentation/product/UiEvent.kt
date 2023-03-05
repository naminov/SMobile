package com.naminov.smobile.presentation.product

import com.naminov.smobile.domain.model.Product

sealed class UiEvent {
    class OnSearchChange(
        val search: String
    ) : UiEvent()

    class OnProductClick(
        val product: Product
    ) : UiEvent()

    class OnLoadProducts(
        val error: Throwable? = null
    ) : UiEvent()

    object OnInitialization : UiEvent()
    object OnExitClick : UiEvent()
}