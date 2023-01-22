package com.naminov.smobile.presentation.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naminov.smobile.R
import com.naminov.smobile.domain.usecase.customer.GetCustomersUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CustomersViewModel(
    private val getCustomersUseCase: GetCustomersUseCase,
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
            is UiEvent.OnCustomerClick -> handleEventOnCustomerClick(uiEvent)
            is UiEvent.OnSearchChange -> handleEventOnSearchChange(uiEvent)
            UiEvent.OnLoad -> handleEventOnLoad()
            UiEvent.OnCancelClick -> handleEventOnCancelClick()
        }
    }

    private fun handleEventOnCustomerClick(uiEvent: UiEvent.OnCustomerClick) {
        viewModelScope.launch {
            _action.emit(UiAction.SetResult(uiEvent.customer))
            _action.emit(UiAction.Close)
        }
    }

    private fun handleEventOnSearchChange(uiEvent: UiEvent.OnSearchChange) {
        _state.value = _state.value.copy(
            search = uiEvent.search
        )

        handleEventOnLoad()
    }

    private fun handleEventOnLoad() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val search = state.value.search
                val customers = getCustomersUseCase(search)
                _state.value = _state.value.copy(
                    customers = customers
                )
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