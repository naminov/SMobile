package com.naminov.smobile.domain.repository

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.Product

interface ProductsRepository {
    fun getProducts(search: String?): PagingSource<Int, Product>
}