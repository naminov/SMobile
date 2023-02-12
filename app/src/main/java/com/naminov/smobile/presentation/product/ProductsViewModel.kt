package com.naminov.smobile.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naminov.smobile.R
import com.naminov.smobile.domain.usecase.product.GetProductsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
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
            is UiEvent.OnProductClick -> handleEventOnProductClick(uiEvent)
            is UiEvent.OnSearchChange -> handleEventOnSearchChange(uiEvent)
            UiEvent.OnLoad -> handleEventOnLoad()
            UiEvent.OnExitClick -> handleEventOnExitClick()
        }
    }

    private fun handleEventOnProductClick(uiEvent: UiEvent.OnProductClick) {
        viewModelScope.launch {
            _action.emit(UiAction.SetResult(uiEvent.product))
            _action.emit(UiAction.Exit)
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
                val products = getProductsUseCase(search)
                _state.value = _state.value.copy(
                    products = products
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnExitClick() {
        viewModelScope.launch {
            _action.emit(UiAction.Exit)
        }
    }
}