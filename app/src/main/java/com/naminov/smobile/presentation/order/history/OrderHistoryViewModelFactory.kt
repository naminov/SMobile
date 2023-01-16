package com.naminov.smobile.presentation.order.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naminov.smobile.domain.usecase.customer.GetCustomerImgUseCase
import com.naminov.smobile.domain.usecase.order.GetOrderHistoryUseCase
import javax.inject.Inject

class OrderHistoryViewModelFactory @Inject constructor(
    private val getCustomerImgUseCase: GetCustomerImgUseCase,
    private val getOrderHistoryUseCase: GetOrderHistoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderHistoryViewModel(getCustomerImgUseCase, getOrderHistoryUseCase) as T
    }
}