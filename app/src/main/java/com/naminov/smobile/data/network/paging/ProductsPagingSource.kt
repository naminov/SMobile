package com.naminov.smobile.data.network.paging

import com.naminov.smobile.data.mapper.toDomain
import com.naminov.smobile.data.network.api.ProductsApi
import com.naminov.smobile.domain.model.Product

class ProductsPagingSource(
    private val productsApi: ProductsApi,
    private val search: String?
) : BasePagingSource<Product>() {
    override suspend fun loadData(page: Int, pageSize: Int): List<Product> {
        return productsApi
            .getProducts(
                search = search?.ifEmpty { null },
                page = page,
                pageSize = pageSize
            )
            .toDomain()
    }

    interface Factory {
        fun create(search: String?): ProductsPagingSource
    }
}