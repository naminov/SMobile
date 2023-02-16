package com.naminov.smobile.presentation.order.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naminov.smobile.domain.usecase.order.*
import javax.inject.Inject

class OrderDetailsViewModelFactory @Inject constructor(
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
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderDetailsViewModel(
            getOrderDetailsUseCase,
            getOrderDetailsNewUseCase,
            editOrderCustomerUseCase,
            editOrderCommentUseCase,
            editOrderProductNumberUseCase,
            addOrderProductUseCase,
            removeOrderProductUseCase,
            hasOrderChangesUseCase,
            removeOrderUseCase,
            saveOrderUseCase
        ) as T
    }
}