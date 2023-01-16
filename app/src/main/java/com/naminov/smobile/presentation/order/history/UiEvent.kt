package com.naminov.smobile.presentation.order.history

import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.model.OrderHistory

sealed class UiEvent {
    class OnSearchChange(
        val search: String
    ) : UiEvent()

    class OnFilterCustomerChange(
        val customer: Customer
    ) : UiEvent()

    class OnFilterNoPaymentChange(
        val noPayment: Boolean
    ) : UiEvent()

    class OnFilterNoDocumentsChange(
        val noDocuments: Boolean
    ) : UiEvent()

    class OnOrderClick(
        val order: OrderHistory
    ) : UiEvent()

    object OnLoad : UiEvent()
    object OnRefresh : UiEvent()
    object OnCreateClick : UiEvent()
    object OnSettingsClick : UiEvent()
    object OnFilterCustomerClick : UiEvent()
    object OnFilterCustomerClearClick : UiEvent()
}