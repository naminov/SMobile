package com.naminov.smobile.domain.repository

import com.naminov.smobile.domain.model.Customer

interface CustomersRepository {
    suspend fun getCustomerImg(customer: Customer): String
}