package com.naminov.smobile.presentation.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.naminov.smobile.R
import com.naminov.smobile.domain.model.Customer
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
            is UiEvent.OnLoadCustomers -> handleEventOnLoadCustomers(uiEvent)
            UiEvent.OnInitialization -> handleEventOnInitialization()
            UiEvent.OnExitClick -> handleEventOnExitClick()
        }
    }

    private fun handleEventOnCustomerClick(uiEvent: UiEvent.OnCustomerClick) {
        viewModelScope.launch {
            _action.emit(UiAction.SetResult(uiEvent.customer))
            _action.emit(UiAction.Exit)
        }
    }

    private fun handleEventOnSearchChange(uiEvent: UiEvent.OnSearchChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                search = uiEvent.search,
                loading = true
            )

            try {
                val search = state.value.search
                val customers = getCustomers(search)

                _state.value = _state.value.copy(customers = customers)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnLoadCustomers(uiEvent: UiEvent.OnLoadCustomers) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                initialized = true,
                loading = false
            )

            uiEvent.error?.let {
                _action.emit(UiAction.ShowMessage(R.string.error_loading))
            }
        }
    }

    private fun handleEventOnInitialization() {
        viewModelScope.launch {
            if (_state.value.initialized) return@launch

            _state.value = _state.value.copy(loading = true)

            try {
                val search = state.value.search
                val customers = getCustomers(search)

                _state.value = _state.value.copy(customers = customers)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnExitClick() {
        viewModelScope.launch {
            _action.emit(UiAction.Exit)
        }
    }

    private fun getCustomers(search: String): Flow<PagingData<Customer>> {
        return Pager(
            PagingConfig(
                pageSize = 15,
                maxSize = 1000,
                enablePlaceholders = false
            )
        ) {
            getCustomersUseCase(search)
        }.flow
            .cachedIn(viewModelScope)
    }
}