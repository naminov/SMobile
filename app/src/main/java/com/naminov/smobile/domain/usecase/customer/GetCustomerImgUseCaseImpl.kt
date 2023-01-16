package com.naminov.smobile.domain.usecase.customer

import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.repository.CustomersRepository

class GetCustomerImgUseCaseImpl(
    private val customersRepository: CustomersRepository
) : GetCustomerImgUseCase {
    override suspend fun invoke(customer: Customer): String {
        return customersRepository.getCustomerImg(customer)
    }
}