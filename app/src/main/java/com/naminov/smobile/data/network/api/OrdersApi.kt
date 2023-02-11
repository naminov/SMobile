package com.naminov.smobile.data.network.api

import com.naminov.smobile.data.network.dto.OrderDetailsDto
import com.naminov.smobile.data.network.dto.OrderEditDto
import com.naminov.smobile.data.network.dto.OrderHistoryDto
import retrofit2.http.*

interface OrdersApi {
    @GET("/orders")
    suspend fun getOrderHistory(
        @Query("search") search: String?,
        @Query("customer") customer: String?,
        @Query("payment") payment: Boolean?,
        @Query("documents") documents: Boolean?
    ): List<OrderHistoryDto>

    @GET("/orders/{id}")
    suspend fun getOrderDetails(
        @Path("id") id: String
    ): OrderDetailsDto

    @POST("/orders/{id}/edit")
    suspend fun editOrder(
        @Path("id") id: String,
        @Body orderEditDto: OrderEditDto
    ): OrderDetailsDto

    @PUT("/orders/{id}")
    suspend fun saveOrder(
        @Path("id") id: String,
        @Body orderEditDto: OrderEditDto
    ): OrderDetailsDto

    @DELETE("/orders/{id}")
    suspend fun removeOrder(
        @Path("id") id: String
    ): OrderDetailsDto

    @GET("/orders/new")
    suspend fun getOrderDetailsNew(
        @Query("customer") customer: String?
    ): OrderDetailsDto

    @POST("/orders/new/edit")
    suspend fun editOrderNew(
        @Body orderEditDto: OrderEditDto
    ): OrderDetailsDto

    @POST("/orders/new")
    suspend fun saveOrderNew(
        @Body orderEditDto: OrderEditDto
    ): OrderDetailsDto
}