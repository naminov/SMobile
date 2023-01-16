package com.naminov.smobile.data.network.api

import com.naminov.smobile.data.network.dto.OrderHistoryDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersApi {
    @GET("/orders")
    suspend fun getOrderHistory(
        @Query("search") search: String?,
        @Query("customer") customer: String?,
        @Query("payment") payment: Boolean?,
        @Query("documents") documents: Boolean?
    ): List<OrderHistoryDto>
}