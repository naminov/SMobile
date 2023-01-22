package com.naminov.smobile.presentation.order.history

import androidx.annotation.StringRes
import com.naminov.smobile.domain.model.OrderHistory

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    class NavigateToOrderDetails(
        val order: OrderHistory
    ) : UiAction()

    object NavigateToSettings : UiAction()
    object NavigateToCustomers : UiAction()
}