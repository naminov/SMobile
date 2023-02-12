package com.naminov.smobile.presentation.order.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naminov.smobile.R
import com.naminov.smobile.domain.usecase.customer.GetCustomerImgUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderHistoryUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderHistoryViewModel(
    private val getCustomerImgUseCase: GetCustomerImgUseCase,
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
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
            is UiEvent.OnSearchChange -> handleEventOnSearchChange(uiEvent)
            is UiEvent.OnFilterCustomerChange -> handleEventOnFilterCustomerChange(uiEvent)
            is UiEvent.OnFilterNoDocumentsChange -> handleEventOnFilterNoDocumentsChange(uiEvent)
            is UiEvent.OnFilterNoPaymentChange -> handleEventOnFilterNoPaymentChange(uiEvent)
            is UiEvent.OnOrderClick -> handleEventOnOrderClick(uiEvent)
            UiEvent.OnLoad -> handleEventOnLoad()
            UiEvent.OnRefresh -> handleEventOnRefresh()
            UiEvent.OnCreateClick -> handleEventOnCreateClick()
            UiEvent.OnSettingsClick -> handleEventOnSettingsClick()
            UiEvent.OnFilterCustomerClick -> handleEventOnFilterCustomerClick()
            UiEvent.OnFilterCustomerClearClick -> handleEventOnFilterCustomerClearClick()
        }
    }

    private fun handleEventOnSearchChange(uiEvent: UiEvent.OnSearchChange) {
        viewModelScope.launch {
            val filter = state.value.filter
            _state.value = _state.value.copy(
                filter = filter.copy(
                    search = uiEvent.search
                )
            )

            handleEventOnLoad()
        }
    }

    private fun handleEventOnFilterCustomerChange(uiEvent: UiEvent.OnFilterCustomerChange) {
        viewModelScope.launch {
            val customerImg = getCustomerImgUseCase(uiEvent.customer)
            val filter = state.value.filter
            _state.value = _state.value.copy(
                filter = filter.copy(
                    customer = uiEvent.customer
                ),
                customerImg = customerImg
            )

            handleEventOnLoad()
        }
    }

    private fun handleEventOnFilterNoDocumentsChange(uiEvent: UiEvent.OnFilterNoDocumentsChange) {
        viewModelScope.launch {
            val filter = state.value.filter
            _state.value = _state.value.copy(
                filter = filter.copy(
                    noDocuments = uiEvent.noDocuments
                )
            )

            handleEventOnLoad()
        }
    }

    private fun handleEventOnFilterNoPaymentChange(uiEvent: UiEvent.OnFilterNoPaymentChange) {
        viewModelScope.launch {
            val filter = state.value.filter
            _state.value = _state.value.copy(
                filter = filter.copy(
                    noPayment = uiEvent.noPayment
                )
            )

            handleEventOnLoad()
        }
    }

    private fun handleEventOnOrderClick(uiEvent: UiEvent.OnOrderClick) {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToOrderDetails(uiEvent.order.id))
        }
    }

    private fun handleEventOnLoad() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val filter = state.value.filter
                val orders = getOrderHistoryUseCase(filter)
                _state.value = _state.value.copy(
                    orders = orders
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnRefresh() {
        handleEventOnLoad()
    }

    private fun handleEventOnCreateClick() {
        viewModelScope.launch {
            val customer = state.value.filter.customer?.id ?: ""
            _action.emit(UiAction.NavigateToOrderCreate(customer))
        }
    }

    private fun handleEventOnSettingsClick() {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToSettings)
        }
    }

    private fun handleEventOnFilterCustomerClick() {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToCustomers)
        }
    }

    private fun handleEventOnFilterCustomerClearClick() {
        viewModelScope.launch {
            val filter = state.value.filter
            _state.value = _state.value.copy(
                filter = filter.copy(
                    customer = null
                ),
                customerImg = ""
            )

            handleEventOnLoad()
        }
    }
}
