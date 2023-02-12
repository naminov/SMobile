package com.naminov.smobile.presentation.customer

import androidx.annotation.StringRes
import com.naminov.smobile.domain.model.Customer

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    class SetResult(
        val customer: Customer
    ) : UiAction()

    object Exit : UiAction()
}