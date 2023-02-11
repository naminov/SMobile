package com.naminov.smobile.data.repository

import com.naminov.smobile.data.mapper.toDomain
import com.naminov.smobile.data.network.api.ProductsApi
import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.domain.repository.ProductsRepository

class ProductsRepositoryImpl(
    private val productsApi: ProductsApi
) : ProductsRepository {
    override suspend fun getProducts(search: String?): List<Product> {
        return productsApi
            .getProducts(search?.ifEmpty { null })
            .toDomain()
    }
}