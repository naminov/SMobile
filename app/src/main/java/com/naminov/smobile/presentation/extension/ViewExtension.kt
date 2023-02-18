package com.naminov.smobile.presentation.extension

import android.view.View
import com.naminov.smobile.presentation.listener.OnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

fun View.setOnSingleClickListener(
    singleClickController: SingleClickController,
    onSingleClick: (v: View) -> Unit
) {
    val onSingleClickListener = OnSingleClickListener(
        singleClickController,
        onSingleClick
    )
    setOnClickListener(onSingleClickListener)
}