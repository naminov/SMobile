package com.naminov.smobile.data.network.api

import com.naminov.smobile.data.network.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts(
        @Query("search") search: String?,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): List<ProductDto>
}