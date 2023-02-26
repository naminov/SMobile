package com.naminov.smobile.domain.usecase.product

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.Product
import com.naminov.smobile.domain.repository.ProductsRepository

class GetProductsUseCaseImpl(
    private val productsRepository: ProductsRepository
) : GetProductsUseCase {
    override fun invoke(search: String): PagingSource<Int, Product> {
        return productsRepository.getProducts(
            search.ifEmpty { null }
        )
    }
}