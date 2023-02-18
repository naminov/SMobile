package com.naminov.smobile.presentation.extension

import android.view.View
import com.google.android.material.chip.Chip
import com.naminov.smobile.presentation.listener.OnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

fun Chip.setOnCloseIconSingleClickListener(
    singleClickController: SingleClickController,
    onSingleClick: (v: View) -> Unit
) {
    val onSingleClickListener = OnSingleClickListener(
        singleClickController,
        onSingleClick
    )
    setOnCloseIconClickListener(onSingleClickListener)
}