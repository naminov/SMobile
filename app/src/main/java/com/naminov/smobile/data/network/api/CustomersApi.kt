package com.naminov.smobile.data.network.api

import com.naminov.smobile.data.network.dto.CustomerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CustomersApi {
    @GET("/customers")
    suspend fun getCustomers(
        @Query("search") search: String?
    ): List<CustomerDto>
}