package com.naminov.smobile.domain.repository

import com.naminov.smobile.domain.model.Product

interface ProductsRepository {
    suspend fun getProducts(search: String?): List<Product>
}