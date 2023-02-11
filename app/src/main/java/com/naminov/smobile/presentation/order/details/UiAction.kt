package com.naminov.smobile.presentation.order.details

import androidx.annotation.StringRes

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    object NavigateToCustomers : UiAction()
    object NavigateToProducts : UiAction()
    object NavigateToSettings : UiAction()
    object ConfirmExitWithoutSave : UiAction()
    object Exit : UiAction()
}