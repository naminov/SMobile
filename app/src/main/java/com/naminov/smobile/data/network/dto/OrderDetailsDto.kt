package com.naminov.smobile.data.network.dto

import com.google.gson.annotations.SerializedName

data class OrderDetailsDto(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("number")
    val number: String,
    @field:SerializedName("date")
    val date: String,
    @field:SerializedName("customer")
    val customer: CustomerDto,
    @field:SerializedName("sum")
    val sum: String,
    @field:SerializedName("products")
    val products: List<OrderDetailsProductDto>,
    @field:SerializedName("comment")
    val comment: String,
    @field:SerializedName("payment")
    val payment: Boolean,
    @field:SerializedName("documents")
    val documents: Boolean,
    @field:SerializedName("completed")
    val completed: Boolean,
    @field:SerializedName("editable")
    val editable: Boolean,
    @field:SerializedName("new")
    val new: Boolean
)