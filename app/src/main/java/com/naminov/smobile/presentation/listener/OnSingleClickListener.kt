package com.naminov.smobile.presentation.listener

import android.view.View

class OnSingleClickListener(
    private val singleClickController: SingleClickController,
    private val onSingleClick: (v: View) -> Unit
) : View.OnClickListener {
    override fun onClick(v: View) {
        if (!singleClickController.isClickAvailable()) {
            return
        }
        onSingleClick(v)
    }
}