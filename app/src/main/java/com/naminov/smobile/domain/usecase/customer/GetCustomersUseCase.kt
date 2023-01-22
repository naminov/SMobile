package com.naminov.smobile.domain.usecase.customer

import com.naminov.smobile.domain.model.Customer

interface GetCustomersUseCase {
    suspend operator fun invoke(search: String): List<Customer>
}