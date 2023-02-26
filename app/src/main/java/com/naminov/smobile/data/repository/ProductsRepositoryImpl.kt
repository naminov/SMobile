package com.naminov.smobile.data.repository

import com.naminov.smobile.data.network.paging.ProductsPagingSource
import com.naminov.smobile.domain.repository.ProductsRepository

class ProductsRepositoryImpl(
    private val productsPagingSourceFactory: ProductsPagingSource.Factory
) : ProductsRepository {
    override fun getProducts(search: String?): ProductsPagingSource {
        return productsPagingSourceFactory
            .create(search)
    }
}