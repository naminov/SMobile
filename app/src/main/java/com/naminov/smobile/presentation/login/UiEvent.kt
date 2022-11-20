package com.naminov.smobile.presentation.login

sealed class UiEvent {
    class OnUserNameChange(
        val userName: String
    ) : UiEvent()

    class OnPasswordChange(
        val password: String
    ) : UiEvent()

    object OnLoginClick : UiEvent()
    object OnSettingsClick : UiEvent()
}