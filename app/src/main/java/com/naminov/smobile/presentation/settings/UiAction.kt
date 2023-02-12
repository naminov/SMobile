package com.naminov.smobile.presentation.settings

import androidx.annotation.StringRes

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    object Exit : UiAction()
}