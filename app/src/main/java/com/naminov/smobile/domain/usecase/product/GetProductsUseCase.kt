package com.naminov.smobile.domain.usecase.product

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.Product

interface GetProductsUseCase {
    operator fun invoke(search: String): PagingSource<Int, Product>
}