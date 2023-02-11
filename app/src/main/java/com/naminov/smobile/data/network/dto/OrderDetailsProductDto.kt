package com.naminov.smobile.data.network.dto

import com.google.gson.annotations.SerializedName

data class OrderDetailsProductDto(
    @field:SerializedName("product")
    val product: ProductDto,
    @field:SerializedName("price")
    val price: String,
    @field:SerializedName("number")
    val number: Int,
    @field:SerializedName("sum")
    val sum: String
)