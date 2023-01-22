package com.naminov.smobile.presentation.customer

import com.naminov.smobile.domain.model.Customer

sealed class UiEvent {
    class OnSearchChange(
        val search: String
    ) : UiEvent()

    class OnCustomerClick(
        val customer: Customer
    ) : UiEvent()

    object OnLoad : UiEvent()
    object OnCancelClick : UiEvent()
}