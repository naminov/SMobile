package com.naminov.smobile.data.repository

import com.naminov.smobile.data.network.Constants
import com.naminov.smobile.data.network.paging.CustomersPagingSource
import com.naminov.smobile.domain.model.Customer
import com.naminov.smobile.domain.repository.CustomersRepository

class CustomersRepositoryImpl(
    private val customersPagingSourceFactory: CustomersPagingSource.Factory
) : CustomersRepository {
    override suspend fun getCustomerImg(customer: Customer): String {
        return Constants.BASE_URL + "customers/${customer.id}/img"
    }

    override fun getCustomers(search: String?): CustomersPagingSource {
        return customersPagingSourceFactory
            .create(search)
    }
}