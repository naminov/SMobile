package com.naminov.smobile.presentation.extension

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.naminov.smobile.presentation.listener.OnSingleClickListener
import com.naminov.smobile.presentation.listener.SingleClickController

fun Toolbar.setNavigationOnSingleClickListener(
    singleClickController: SingleClickController,
    onSingleClick: (v: View) -> Unit
) {
    val onSingleClickListener = OnSingleClickListener(
        singleClickController,
        onSingleClick
    )
    setNavigationOnClickListener(onSingleClickListener)
}