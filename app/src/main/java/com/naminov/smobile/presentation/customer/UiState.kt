package com.naminov.smobile.presentation.customer

import androidx.paging.PagingData
import com.naminov.smobile.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class UiState(
    val initialized: Boolean = false,
    val search: String = "",
    val customers: Flow<PagingData<Customer>> = flow {
        PagingData.empty<Customer>()
    },
    val loading: Boolean = false
)