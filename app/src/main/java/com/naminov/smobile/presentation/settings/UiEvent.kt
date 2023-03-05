package com.naminov.smobile.presentation.settings

sealed class UiEvent {
    class OnConnectionUrlChange(
        val connectionUrl: String
    ) : UiEvent()

    object OnInitialization : UiEvent()
    object OnSaveClick : UiEvent()
    object OnExitClick : UiEvent()
}