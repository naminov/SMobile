package com.naminov.smobile.presentation.product

import androidx.annotation.StringRes
import com.naminov.smobile.domain.model.Product

sealed class UiAction {
    class ShowMessage(
        @StringRes val messageId: Int
    ) : UiAction()

    class SetResult(
        val product: Product
    ) : UiAction()

    object Exit : UiAction()
}