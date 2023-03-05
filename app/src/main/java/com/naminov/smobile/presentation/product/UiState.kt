package com.naminov.smobile.presentation.product

import androidx.paging.PagingData
import com.naminov.smobile.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class UiState(
    val initialized: Boolean = false,
    val search: String = "",
    val products: Flow<PagingData<Product>> = flow {
        PagingData.empty<Product>()
    },
    val loading: Boolean = false
)