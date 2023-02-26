package com.naminov.smobile.data.network.paging

import com.naminov.smobile.data.mapper.toDomain
import com.naminov.smobile.data.network.api.CustomersApi
import com.naminov.smobile.domain.model.Customer

class CustomersPagingSource(
    private val customersApi: CustomersApi,
    private val search: String?
) : BasePagingSource<Customer>() {
    override suspend fun loadData(page: Int, pageSize: Int): List<Customer> {
        return customersApi
            .getCustomers(
                search = search?.ifEmpty { null },
                page = page,
                pageSize = pageSize
            )
            .toDomain()
    }

    interface Factory {
        fun create(search: String?): CustomersPagingSource
    }
}