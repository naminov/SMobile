package com.naminov.smobile.presentation.order.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naminov.smobile.R
import com.naminov.smobile.domain.usecase.order.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderDetailsViewModel(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderDetailsNewUseCase: GetOrderDetailsNewUseCase,
    private val editOrderCustomerUseCase: EditOrderCustomerUseCase,
    private val editOrderCommentUseCase: EditOrderCommentUseCase,
    private val editOrderProductNumberUseCase: EditOrderProductNumberUseCase,
    private val addOrderProductUseCase: AddOrderProductUseCase,
    private val removeOrderProductUseCase: RemoveOrderProductUseCase,
    private val hasOrderChangesUseCase: HasOrderChangesUseCase,
    private val removeOrderUseCase: RemoveOrderUseCase,
    private val saveOrderUseCase: SaveOrderUseCase
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
            is UiEvent.OnCustomerChange -> handleEventOnCustomerChange(uiEvent)
            is UiEvent.OnCommentChange -> handleEventOnCommentChange(uiEvent)
            is UiEvent.OnProductNumberChange -> handleEventOnProductNumberChange(uiEvent)
            is UiEvent.OnProductAdd -> handleEventOnProductAdd(uiEvent)
            is UiEvent.OnProductRemove -> handleEventOnProductRemove(uiEvent)
            is UiEvent.OnInitialization -> handleEventOnInitialization(uiEvent)
            UiEvent.OnCustomerClick -> handleEventOnCustomerClick()
            UiEvent.OnProductAddClick -> handleEventOnProductAddClick()
            UiEvent.OnSaveClick -> handleEventOnSaveClick()
            UiEvent.OnCopyClick -> handleEventOnCopyClick()
            UiEvent.OnCopyConfirm -> handleEventOnCopyConfirm()
            UiEvent.OnRemoveClick -> handleEventOnRemoveClick()
            UiEvent.OnRemoveConfirm -> handleEventOnRemoveConfirm()
            UiEvent.OnSettingsClick -> handleEventOnSettingsClick()
            UiEvent.OnExitClick -> handleEventOnExitClick()
            UiEvent.OnExitConfirmWithoutSave -> handleEventOnExitConfirmWithoutSave()
        }
    }

    private fun handleEventOnCustomerChange(uiEvent: UiEvent.OnCustomerChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = editOrderCustomerUseCase(
                    _state.value.orderDetails,
                    uiEvent.customer
                )

                _state.value = _state.value.copy(
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnCommentChange(uiEvent: UiEvent.OnCommentChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = editOrderCommentUseCase(
                    _state.value.orderDetails,
                    uiEvent.comment
                )

                _state.value = _state.value.copy(
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnProductNumberChange(uiEvent: UiEvent.OnProductNumberChange) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = editOrderProductNumberUseCase(
                    _state.value.orderDetails,
                    uiEvent.product,
                    uiEvent.number
                )

                _state.value = _state.value.copy(
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnProductAdd(uiEvent: UiEvent.OnProductAdd) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = addOrderProductUseCase(
                    _state.value.orderDetails,
                    uiEvent.product
                )

                _state.value = _state.value.copy(
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnProductRemove(uiEvent: UiEvent.OnProductRemove) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = removeOrderProductUseCase(
                    _state.value.orderDetails,
                    uiEvent.product
                )

                _state.value = _state.value.copy(
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnInitialization(uiEvent: UiEvent.OnInitialization) {
        viewModelScope.launch {
            if (_state.value.initialized) return@launch

            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = if (uiEvent.new) {
                    getOrderDetailsNewUseCase(uiEvent.order, uiEvent.customer)
                } else {
                    getOrderDetailsUseCase(uiEvent.order)
                }

                _state.value = _state.value.copy(
                    orderDetailsOrig = orderDetails,
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(
                    initialized = true,
                    loading = false
                )
            }
        }
    }

    private fun handleEventOnCustomerClick() {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToCustomers)
        }
    }

    private fun handleEventOnProductAddClick() {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToProducts)
        }
    }

    private fun handleEventOnSaveClick() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = saveOrderUseCase(
                    _state.value.orderDetails
                )

                _state.value = _state.value.copy(
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
                return@launch
            } finally {
                _state.value = _state.value.copy(loading = false)
            }

            _action.emit(UiAction.Exit)
        }
    }

    private fun handleEventOnCopyClick() {
        viewModelScope.launch {
            val hasChanges = hasOrderChangesUseCase(
                state.value.orderDetailsOrig,
                state.value.orderDetails
            )

            if (hasChanges) {
                _action.emit(UiAction.CopyConfirm)
                return@launch
            }

            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = getOrderDetailsNewUseCase(
                    _state.value.orderDetails.id,
                    ""
                )

                _state.value = _state.value.copy(
                    orderDetailsOrig = orderDetails,
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnCopyConfirm() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                val orderDetails = getOrderDetailsNewUseCase(
                    _state.value.orderDetails.id,
                    ""
                )

                _state.value = _state.value.copy(
                    orderDetailsOrig = orderDetails,
                    orderDetails = orderDetails
                )
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
            } finally {
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    private fun handleEventOnRemoveClick() {
        viewModelScope.launch {
            _action.emit(UiAction.RemoveConfirm)
        }
    }

    private fun handleEventOnRemoveConfirm() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            try {
                removeOrderUseCase(_state.value.orderDetails.id)
            } catch (e: Exception) {
                _action.emit(UiAction.ShowMessage(R.string.error))
                return@launch
            } finally {
                _state.value = _state.value.copy(loading = false)
            }

            _action.emit(UiAction.Exit)
        }
    }

    private fun handleEventOnSettingsClick() {
        viewModelScope.launch {
            _action.emit(UiAction.NavigateToSettings)
        }
    }

    private fun handleEventOnExitClick() {
        viewModelScope.launch {
            val hasChanges = hasOrderChangesUseCase(
                state.value.orderDetailsOrig,
                state.value.orderDetails
            )

            if (hasChanges) {
                _action.emit(UiAction.ExitConfirmWithoutSave)
            } else {
                _action.emit(UiAction.Exit)
            }
        }
    }

    private fun handleEventOnExitConfirmWithoutSave() {
        viewModelScope.launch {
            _action.emit(UiAction.Exit)
        }
    }
}
