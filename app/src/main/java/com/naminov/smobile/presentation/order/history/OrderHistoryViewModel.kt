package com.naminov.smobile.presentation.order.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.naminov.smobile.R
import com.naminov.smobile.domain.model.OrderHistory
import com.naminov.smobile.domain.model.OrderHistoryFilter
import com.naminov.smobile.domain.usecase.customer.GetCustomerImgUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderHistoryUseCase
import com.naminov.smobile.domain.usecase.order.RemoveOrderUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderHistoryViewModel(
    private val getCustomerImgUseCase: GetCustomerImgUseCase,
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase,
    private val removeOrderUseCase: RemoveOrderUseCase
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
            is UiEvent.OnOrderCopyClick -> handleEventOnOrderCopyClick(uiEvent)
            is UiEvent.OnOrderRemoveClick -> handleEventOnOrderRemoveClick(uiEvent)
            is UiEvent.OnOrderRemoveConfirm -> handleEventOnOrderRemoveConfirm(uiEvent)
            is UiEvent.OnLoadOrders -> handleEventOnLoadOrders(uiEvent)
            UiEvent.OnInitialization -> handleEventOnInitialization()
            UiEvent.OnRefresh -> handleEventOnRefresh()
            UiEvent.OnCreateClick -> handleEventOnCreateClick()
            UiEvent.OnSettingsClick -> handleEventOnSettingsClick()
            UiEvent.OnFilterCustomerClick -> handleEventOnFilterCustomerClick()
            UiEvent.OnFilterCustomerClearClick -> handleEventOnFilterCustomerClearClick()
        }
    }

    private fun handleEventOnSearchChange(uiEvent: UiEvent.OnSearchChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val filter = state.value.filter.copy(
                    search = uiEvent.search
                )
                val orders = getOrders(filter)

                _state.value = _state.value.copy(
                    filter = filter,
                    orders = orders
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnFilterCustomerChange(uiEvent: UiEvent.OnFilterCustomerChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val customerImg = getCustomerImgUseCase(uiEvent.customer)
                val filter = state.value.filter.copy(
                    customer = uiEvent.customer
                )
                val orders = getOrders(filter)

                _state.value = _state.value.copy(
                    filter = filter,
                    customerImg = customerImg,
                    orders = orders
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnFilterNoDocumentsChange(uiEvent: UiEvent.OnFilterNoDocumentsChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val filter = state.value.filter.copy(
                    noDocuments = uiEvent.noDocuments
                )
                val orders = getOrders(filter)

                _state.value = _state.value.copy(
                    filter = filter,
                    orders = orders
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnFilterNoPaymentChange(uiEvent: UiEvent.OnFilterNoPaymentChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val filter = state.value.filter.copy(
                    noPayment = uiEvent.noPayment
                )
                val orders = getOrders(filter)

                _state.value = _state.value.copy(
                    filter = filter,
                    orders = orders
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnOrderClick(uiEvent: UiEvent.OnOrderClick) {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToOrderDetails(uiEvent.order.id))
        }
    }

    private fun handleEventOnOrderCopyClick(uiEvent: UiEvent.OnOrderCopyClick) {
        viewModelScope.launch {
            val order = uiEvent.order.id
            _action.emit(UiAction.NavigateToOrderCreate(order, ""))
        }
    }

    private fun handleEventOnOrderRemoveClick(uiEvent: UiEvent.OnOrderRemoveClick) {
        viewModelScope.launch {
            _action.emit(UiAction.OrderRemoveConfirm(uiEvent.order))
        }
    }

    private fun handleEventOnOrderRemoveConfirm(uiEvent: UiEvent.OnOrderRemoveConfirm) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                removeOrderUseCase.invoke(uiEvent.order.id)

                val filter = state.value.filter
                val orders = getOrders(filter)

                _state.value = _state.value.copy(orders = orders)
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnLoadOrders(uiEvent: UiEvent.OnLoadOrders) {
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
                val filter = state.value.filter
                val orders = getOrders(filter)

                _state.value = _state.value.copy(orders = orders)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnRefresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val filter = state.value.filter
                val orders = getOrders(filter)

                _state.value = _state.value.copy(orders = orders)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnCreateClick() {
        viewModelScope.launch {
            val customer = state.value.filter.customer?.id ?: ""
            _action.emit(UiAction.NavigateToOrderCreate("", customer))
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
            _state.value = _state.value.copy(loading = true)

            try {
                val filter = state.value.filter.copy(
                    customer = null
                )
                val orders = getOrders(filter)

                _state.value = _state.value.copy(
                    filter = filter,
                    customerImg = "",
                    orders = orders
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun getOrders(filter: OrderHistoryFilter): Flow<PagingData<OrderHistory>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                maxSize = 1000,
                enablePlaceholders = false
            )
        ) {
            getOrderHistoryUseCase(filter)
        }.flow
            .cachedIn(viewModelScope)
    }
}
