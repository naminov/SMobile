package com.naminov.smobile.presentation.order.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naminov.smobile.domain.usecase.customer.GetCustomerImgUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderHistoryUseCase
import com.naminov.smobile.domain.usecase.order.RemoveOrderUseCase
import javax.inject.Inject

class OrderHistoryViewModelFactory @Inject constructor(
    private val getCustomerImgUseCase: GetCustomerImgUseCase,
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase,
    private val removeOrderUseCase: RemoveOrderUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderHistoryViewModel(
            getCustomerImgUseCase,
            getOrderHistoryUseCase,
            removeOrderUseCase
        ) as T
    }
}