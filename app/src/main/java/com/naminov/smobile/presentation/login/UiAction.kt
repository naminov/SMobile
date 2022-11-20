package com.naminov.smobile.presentation.login

import androidx.annotation.StringRes

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    object HideKeyboard: UiAction()
    object NavigateToSettings: UiAction()
    object NavigateToOrderHistory: UiAction()
}