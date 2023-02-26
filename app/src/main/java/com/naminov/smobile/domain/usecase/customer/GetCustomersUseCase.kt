package com.naminov.smobile.domain.usecase.customer

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.Customer

interface GetCustomersUseCase {
    operator fun invoke(search: String): PagingSource<Int, Customer>
}