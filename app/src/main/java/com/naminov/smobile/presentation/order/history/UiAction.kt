package com.naminov.smobile.presentation.order.history

import androidx.annotation.StringRes

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    class NavigateToOrderDetails(
        val order: String
    ) : UiAction()

    class NavigateToOrderCreate(
        val customer: String
    ) : UiAction()

    object NavigateToSettings : UiAction()
    object NavigateToCustomers : UiAction()
}