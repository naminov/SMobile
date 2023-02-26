package com.naminov.smobile.domain.usecase.customer

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.repository.CustomersRepository

class GetCustomersUseCaseImpl(
    private val customersRepository: CustomersRepository
): GetCustomersUseCase {
    override fun invoke(search: String): PagingSource<Int, Customer> {
        return customersRepository.getCustomers(
            search.ifEmpty { null }
        )
    }
}