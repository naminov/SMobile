package com.naminov.smobile.domain.usecase.product

import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.domain.repository.ProductsRepository

class GetProductsUseCaseImpl(
    private val productsRepository: ProductsRepository
) : GetProductsUseCase {
    override suspend fun invoke(search: String): List<Product> {
        return productsRepository.getProducts(
            search.ifEmpty { null }
        )
    }
}