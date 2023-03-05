package com.naminov.smobile.presentation.settings

data class UiState(
    val initialized: Boolean = false,
    val connectionUrl: String = "",
    val loading: Boolean = false
)