package com.naminov.smobile.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naminov.smobile.R
import com.naminov.smobile.domain.usecase.settings.GetSettingsUseCase
import com.naminov.smobile.domain.usecase.settings.SaveSettingsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _action = MutableSharedFlow<UiAction>()
    val action: SharedFlow<UiAction> = _action.asSharedFlow()

    val event = MutableSharedFlow<UiEvent>()

    init {
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }
    }

    private fun handleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.OnConnectionUrlChange -> handleEventOnConnectionUrlChange(uiEvent)
            UiEvent.OnLoad -> handleEventOnLoad()
            UiEvent.OnSaveClick -> handleEventOnSaveClick()
            UiEvent.OnCancelClick -> handleEventOnCancelClick()
        }
    }

    private fun handleEventOnConnectionUrlChange(uiEvent: UiEvent.OnConnectionUrlChange) {
        viewModelScope.launch {
            if (uiEvent.connectionUrl == _state.value.connectionUrl) {
                return@launch
            }
            _state.value = _state.value.copy(
                connectionUrl = uiEvent.connectionUrl
            )
        }
    }

    private fun handleEventOnLoad() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val settings = getSettingsUseCase()
                _state.value = _state.value.copy(
                    connectionUrl = settings.connection.url
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            }

           _state.value = _state.value.copy(loading = false)
        }
    }

    private fun handleEventOnSaveClick() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val settings = getSettingsUseCase()
                val settingsNew = settings.copy(
                    connection = settings.connection.copy(
                        url = _state.value.connectionUrl
                    )
                )
                saveSettingsUseCase(settingsNew)

                _action.emit(UiAction.Close)
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            }

            _state.value = _state.value.copy(loading = false)
        }
    }

    private fun handleEventOnCancelClick() {
        viewModelScope.launch {
            _action.emit(UiAction.Close)
        }
    }
}