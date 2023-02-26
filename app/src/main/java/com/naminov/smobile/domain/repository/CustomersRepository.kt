package com.naminov.smobile.domain.repository

import androidx.paging.PagingSource
import com.naminov.smobile.domain.model.Customer

interface CustomersRepository {
    suspend fun getCustomerImg(customer: Customer): String
    fun getCustomers(search: String?): PagingSource<Int, Customer>
}