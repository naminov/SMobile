package com.naminov.smobile.presentation.order.details

import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.model.OrderDetailsProduct
import com.naminov.smobile.domain.model.Product

sealed class UiEvent {
    class OnCustomerChange(
        val customer: Customer
    ) : UiEvent()

    class OnCommentChange(
        val comment: String
    ) : UiEvent()

    class OnProductNumberChange(
        val product: OrderDetailsProduct,
        val number: Int
    ) : UiEvent()

    class OnProductAdd(
        val product: Product
    ) : UiEvent()

    class OnProductRemove(
        val product: OrderDetailsProduct
    ) : UiEvent()

    class OnLoad(
        val new: Boolean,
        val order: String,
        val customer: String
    ) : UiEvent()

    object OnCustomerClick : UiEvent()
    object OnProductAddClick : UiEvent()
    object OnSaveClick : UiEvent()
    object OnRemoveClick : UiEvent()
    object OnRemoveConfirm : UiEvent()
    object OnSettingsClick : UiEvent()
    object OnExitClick : UiEvent()
    object OnExitConfirmWithoutSave : UiEvent()
}