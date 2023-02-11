package com.naminov.smobile.domain.usecase.product

import com.naminov.smobile.domain.model.Product

interface GetProductsUseCase {
    suspend operator fun invoke(search: String): List<Product>
}