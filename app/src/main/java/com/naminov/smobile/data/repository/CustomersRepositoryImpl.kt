package com.naminov.smobile.data.repository

import com.naminov.smobile.data.network.Constants
import com.naminov.smobile.data.network.api.CustomersApi
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.repository.CustomersRepository

class CustomersRepositoryImpl(
    private val customersApi: CustomersApi
): CustomersRepository {
    override suspend fun getCustomerImg(customer: Customer): String {
        return Constants.BASE_URL + "customers/${customer.id}/img"
    }

    override suspend fun getCustomers(search: String?): List<Customer> {
        return customersApi.getCustomers(search)
            .map {
                Customer(
                    it.id,
                    it.name
                )
            }
    }
}