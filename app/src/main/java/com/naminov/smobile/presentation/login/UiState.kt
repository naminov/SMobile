package com.naminov.smobile.presentation.login

data class UiState(
    val userName: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val loginAvailable: Boolean = false
)