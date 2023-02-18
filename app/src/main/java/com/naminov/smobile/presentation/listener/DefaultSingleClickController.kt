package com.naminov.smobile.presentation.listener

class DefaultSingleClickController : SingleClickController {
    companion object {
        const val LOCK_DURATION = 1000
    }

    private var lastClickTime = 0L

    override fun isClickAvailable(): Boolean {
        val clickTime = System.currentTimeMillis()
        val duration = clickTime - lastClickTime
        if (duration < LOCK_DURATION) return false
        lastClickTime = clickTime
        return true
    }
}