package com.naminov.smobile.presentation.settings

sealed class UiEvent {
    class OnConnectionUrlChange(
        val connectionUrl: String
    ) : UiEvent()

    object OnLoad : UiEvent()
    object OnSaveClick : UiEvent()
    object OnCancelClick : UiEvent()
}