package com.naminov.smobile.data.repository

import com.naminov.smobile.data.network.Constants
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.repository.CustomersRepository

class CustomersRepositoryImpl: CustomersRepository {
    override suspend fun getCustomerImg(customer: Customer): String {
        return Constants.BASE_URL + "customers/${customer.id}/img"
    }
}