package com.naminov.smobile.domain.usecase.customer

import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.repository.CustomersRepository

class GetCustomersUseCaseImpl(
    private val customersRepository: CustomersRepository
): GetCustomersUseCase {
    override suspend fun invoke(search: String): List<Customer> {
        return customersRepository.getCustomers(
            search.ifEmpty { null }
        )
    }
}