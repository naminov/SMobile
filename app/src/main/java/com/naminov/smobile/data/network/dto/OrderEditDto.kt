package com.naminov.smobile.data.network.dto

import com.google.gson.annotations.SerializedName

data class OrderEditDto(
    @field:SerializedName("customer")
    val customer: String,
    @field:SerializedName("products")
    val products: List<OrderEditProductDto>,
    @field:SerializedName("comment")
    val comment: String
)