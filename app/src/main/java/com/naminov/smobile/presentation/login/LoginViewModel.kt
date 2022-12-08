package com.naminov.smobile.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naminov.smobile.R
import com.naminov.smobile.domain.model.settings.Authorization
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import com.naminov.smobile.domain.usecase.login.LoginUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.Exception

class LoginViewModel(
    private val getAuthorizationUseCase: GetAuthorizationUseCase,
    private val loginUseCase: LoginUseCase
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
            is UiEvent.OnUserNameChange -> handleEventOnUserNameChange(uiEvent)
            is UiEvent.OnPasswordChange -> handleEventOnPasswordChange(uiEvent)
            UiEvent.OnLoad -> handleEventOnLoad()
            UiEvent.OnLoginClick -> handleEventOnLoginClick()
            UiEvent.OnSettingsClick -> handleEventOnSettingsClick()
        }
    }

    private fun handleEventOnUserNameChange(uiEvent: UiEvent.OnUserNameChange) {
        viewModelScope.launch {
            if (uiEvent.userName == _state.value.userName) {
                return@launch
            }
            _state.value = _state.value.copy(
                userName = uiEvent.userName,
                loginAvailable = uiEvent.userName.isNotEmpty() && _state.value.password.isNotEmpty()
            )
        }
    }

    private fun handleEventOnPasswordChange(uiEvent: UiEvent.OnPasswordChange) {
        viewModelScope.launch {
            if (uiEvent.password == _state.value.password) {
                return@launch
            }
            _state.value = _state.value.copy(
                password = uiEvent.password,
                loginAvailable = uiEvent.password.isNotEmpty() && _state.value.userName.isNotEmpty()
            )
        }
    }

    private fun handleEventOnLoad() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val authorization = getAuthorizationUseCase()
                _state.value = _state.value.copy(
                    userName = authorization.userName
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            }

            _state.value = _state.value.copy(loading = false)
        }
    }

    private fun handleEventOnLoginClick() {
        viewModelScope.launch {
            _action.emit(UiAction.HideKeyboard)

            _state.value = _state.value.copy(loading = true)

            try {
                val authorization = Authorization(
                    _state.value.userName,
                    _state.value.password
                )
                loginUseCase(authorization)

                _action.emit(UiAction.NavigateToOrderHistory)
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            }

            _state.value = _state.value.copy(loading = false)
        }
    }

    private fun handleEventOnSettingsClick() {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToSettings)
        }
    }
}