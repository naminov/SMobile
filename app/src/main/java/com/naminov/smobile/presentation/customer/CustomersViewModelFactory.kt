package com.naminov.smobile.presentation.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.naminov.smobile.domain.usecase.customer.GetCustomersUseCase
import javax.inject.Inject

class CustomersViewModelFactory @Inject constructor(
    private val getCustomersUseCase: GetCustomersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomersViewModel(getCustomersUseCase) as T
    }
}