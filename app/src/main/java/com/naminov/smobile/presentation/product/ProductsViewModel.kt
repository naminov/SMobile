package com.naminov.smobile.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.naminov.smobile.R
import com.naminov.smobile.domain.model.Product
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
            is UiEvent.OnLoadProducts -> handleEventOnLoadProducts(uiEvent)
            UiEvent.OnInitialization -> handleEventOnInitialization()
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
        viewModelScope.launch {
            _state.value = _state.value.copy(
                search = uiEvent.search,
                loading = true
            )

            try {
                val search = state.value.search
                val products = getProducts(search)

                _state.value = _state.value.copy(products = products)
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false)
                _action.emit(UiAction.ShowMessage(R.string.error))
            }
        }
    }

    private fun handleEventOnLoadProducts(uiEvent: UiEvent.OnLoadProducts) {
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
                val products = getProducts(search)

                _state.value = _state.value.copy(products = products)
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

    private fun getProducts(search: String): Flow<PagingData<Product>> {
        return Pager(
            PagingConfig(
                pageSize = 15,
                maxSize = 1000,
                enablePlaceholders = false
            )
        ) {
            getProductsUseCase(search)
        }.flow
            .cachedIn(viewModelScope)
    }
}